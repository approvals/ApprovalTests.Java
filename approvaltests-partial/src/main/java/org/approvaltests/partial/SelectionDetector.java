package org.approvaltests.partial;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.MethodReferenceExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.resolution.declarations.ResolvedConstructorDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedMethodLikeDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedValueDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedFieldDeclaration;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SelectionDetector
{
  public static final String[]  OUR_PACKAGES = {"org.approvaltests.", "com.spun.", "org.lambda.",
                                                "org.packagesettings."};
  private static final Set<String> SKIPPED     = Set.of("target", "build", "out", "node_modules", ".git", ".local",
      ".idea", ".gradle");
  private static final Pattern  JAR_VERSION  = Pattern
      .compile("com/approvaltests/approvaltests/([^/]+)/approvaltests-\\1\\.jar$");
  public static class Result
  {
    public String       approvaltestsVersion;
    public Set<String>  entryPoints = new TreeSet<>();
    public List<String> unresolved  = new ArrayList<>();
    public int          javaFiles;
  }
  public static Result detect(File projectDirectory, List<File> classpath)
  {
    List<File> files = javaFiles(projectDirectory.toPath());
    Result result = new Result();
    result.javaFiles = files.size();
    result.approvaltestsVersion = versionFrom(classpath);
    JavaParser parser = new JavaParser(new ParserConfiguration()
        .setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_17)
        .setSymbolResolver(new JavaSymbolSolver(typeSolver(files, classpath))));
    for (File file : files)
    {
      try
      {
        ParseResult<CompilationUnit> parsed = parser.parse(file);
        parsed.getResult().ifPresent(cu -> scan(cu, file, projectDirectory, result));
      }
      catch (IOException e)
      {
        throw new RuntimeException(e);
      }
    }
    return result;
  }

  private static void scan(CompilationUnit cu, File file, File projectDirectory, Result result)
  {
    String where = projectDirectory.toPath().relativize(file.toPath()).toString();
    for (ImportDeclaration declaration : cu.getImports())
    {
      String name = declaration.getNameAsString();
      if (!isOurs(name) || declaration.isAsterisk())
      {
        continue;
      }
      result.entryPoints.add(declaration.isStatic() ? name.substring(0, name.lastIndexOf('.')) : name);
    }
    for (MethodCallExpr call : cu.findAll(MethodCallExpr.class))
    {
      record(result, where, call, () -> signature(call.resolve()));
    }
    for (MethodReferenceExpr reference : cu.findAll(MethodReferenceExpr.class))
    {
      record(result, where, reference, () -> signature(reference.resolve()));
    }
    for (ObjectCreationExpr creation : cu.findAll(ObjectCreationExpr.class))
    {
      record(result, where, creation, () -> signature(creation.resolve()));
    }
    for (FieldAccessExpr access : cu.findAll(FieldAccessExpr.class))
    {
      record(result, where, access, () -> field(access.resolve()));
    }
    for (NameExpr name : cu.findAll(NameExpr.class))
    {
      if (!isScope(name))
      {
        record(result, where, name, () -> field(name.resolve()));
      }
    }
    for (ClassOrInterfaceType type : cu.findAll(ClassOrInterfaceType.class))
    {
      record(result, where, type, () -> type.resolve().erasure().describe());
    }
  }

  private static boolean isScope(NameExpr name)
  {
    return name.getParentNode()
        .filter(p -> (p instanceof MethodCallExpr && ((MethodCallExpr) p).getScope().filter(name::equals).isPresent())
            || (p instanceof FieldAccessExpr && ((FieldAccessExpr) p).getScope() == name)
            || (p instanceof MethodReferenceExpr && ((MethodReferenceExpr) p).getScope() == name))
        .isPresent();
  }

  private static void record(Result result, String where, Object node, Supplier<String> entryPoint)
  {
    try
    {
      String found = entryPoint.get();
      if (found != null && isOurs(found))
      {
        result.entryPoints.add(found);
      }
    }
    catch (Exception | StackOverflowError e)
    {
      String text = node.toString();
      if (Stream.of("Approvals", "Options", "Reporter", "Namer", "Writer").anyMatch(text::contains))
      {
        result.unresolved.add(where + ": " + text);
      }
    }
  }

  private static String signature(ResolvedMethodLikeDeclaration method)
  {
    List<String> parameters = new ArrayList<>();
    for (int i = 0; i < method.getNumberOfParams(); i++)
    {
      parameters.add(erase(method.getParam(i).getType()));
    }
    String owner = method.declaringType().getQualifiedName();
    String name = method instanceof ResolvedConstructorDeclaration ? method.declaringType().getName()
        : method.getName();
    return owner + "." + name + "(" + String.join(", ", parameters) + ")";
  }

  private static String field(ResolvedValueDeclaration value)
  {
    if (!value.isField())
    { return null; }
    ResolvedFieldDeclaration field = value.asField();
    return field.declaringType().getQualifiedName() + "." + field.getName();
  }

  private static String erase(ResolvedType type)
  {
    if (type.isArray())
    { return erase(type.asArrayType().getComponentType()) + "[]"; }
    if (type.isTypeVariable())
    {
      List<com.github.javaparser.resolution.declarations.ResolvedTypeParameterDeclaration.Bound> bounds = type
          .asTypeParameter().getBounds();
      return bounds.isEmpty() ? "java.lang.Object" : erase(bounds.get(0).getType());
    }
    if (type.isReferenceType())
    { return type.asReferenceType().getQualifiedName(); }
    return type.erasure().describe();
  }

  private static boolean isOurs(String name)
  {
    return Stream.of(OUR_PACKAGES).anyMatch(name::startsWith);
  }

  private static CombinedTypeSolver typeSolver(List<File> files, List<File> classpath)
  {
    CombinedTypeSolver solver = new CombinedTypeSolver(new ReflectionTypeSolver(true));
    for (File root : sourceRoots(files))
    {
      solver.add(new JavaParserTypeSolver(root,
          new ParserConfiguration().setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_17)));
    }
    for (File jar : classpath)
    {
      if (jar.getName().endsWith(".jar") && jar.exists())
      {
        try
        {
          solver.add(new JarTypeSolver(jar));
        }
        catch (IOException e)
        {
          throw new RuntimeException(e);
        }
      }
    }
    return solver;
  }

  private static Set<File> sourceRoots(List<File> files)
  {
    Pattern packagePattern = Pattern.compile("^\\s*package\\s+([\\w.]+)\\s*;", Pattern.MULTILINE);
    Set<File> roots = new LinkedHashSet<>();
    for (File file : files)
    {
      Matcher matcher = packagePattern.matcher(com.spun.util.io.FileUtils.readFile(file));
      File root = file.getParentFile();
      if (matcher.find())
      {
        for (int i = 0; i < matcher.group(1).split("\\.").length && root != null; i++)
        {
          root = root.getParentFile();
        }
      }
      if (root != null)
      {
        roots.add(root);
      }
    }
    return roots;
  }

  private static String versionFrom(List<File> classpath)
  {
    for (File jar : classpath)
    {
      Matcher matcher = JAR_VERSION.matcher(jar.getPath().replace(File.separatorChar, '/'));
      if (matcher.find())
      { return matcher.group(1); }
    }
    return null;
  }

  private static List<File> javaFiles(Path root)
  {
    try (Stream<Path> paths = Files.walk(root))
    {
      return paths
          .filter(p -> p.toString().endsWith(".java")
              && root.relativize(p).toString().isEmpty() == false
              && Stream.of(root.relativize(p).toString().split("[/\\\\]")).noneMatch(SKIPPED::contains))
          .map(Path::toFile).sorted().collect(Collectors.toList());
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }
}
