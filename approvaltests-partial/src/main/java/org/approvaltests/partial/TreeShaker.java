package org.approvaltests.partial;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.RecordDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.MethodReferenceExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithImplements;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.spun.util.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TreeShaker
{
  public static final String[]                          SOURCE_ROOTS   = {"approvaltests-util/src/main/java",
                                                                          "approvaltests/src/main/java"};
  private final File                                    checkout;
  private final ShakeReport                             report         = new ShakeReport();
  private final Map<CompilationUnit, File>              files          = new IdentityHashMap<>();
  private final Map<String, TypeDeclaration<?>>         typesByName    = new HashMap<>();
  private final Map<String, MethodDeclaration>          methodsByKey   = new HashMap<>();
  private final Map<String, List<MethodDeclaration>>    methodsByName  = new HashMap<>();
  private final Set<TypeDeclaration<?>>                 keptTypes      = identitySet();
  private final Set<CallableDeclaration<?>>             reachable      = identitySet();
  private final Set<String>                             calledNames    = new HashSet<>();
  private final Deque<Node>                             work           = new ArrayDeque<>();
  public static ShakeReport shake(File checkout, List<String> entryPoints)
  {
    TreeShaker shaker = new TreeShaker(checkout);
    shaker.mark(entryPoints);
    shaker.sweep();
    return shaker.report;
  }

  private TreeShaker(File checkout)
  {
    this.checkout = checkout;
    ParserConfiguration typeSolverConfiguration = configuration();
    CombinedTypeSolver solver = new CombinedTypeSolver(new ReflectionTypeSolver(true));
    for (String root : SOURCE_ROOTS)
    {
      solver.add(new JavaParserTypeSolver(new File(checkout, root), typeSolverConfiguration));
    }
    JavaParser parser = new JavaParser(configuration().setSymbolResolver(new JavaSymbolSolver(solver)));
    for (String root : SOURCE_ROOTS)
    {
      for (File file : javaFiles(new File(checkout, root)))
      {
        parse(parser, file);
      }
    }
    report.totalFiles = files.size();
  }

  private static ParserConfiguration configuration()
  {
    return new ParserConfiguration().setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_17);
  }

  private void parse(JavaParser parser, File file)
  {
    try
    {
      ParseResult<CompilationUnit> result = parser.parse(file);
      if (!result.isSuccessful())
      { throw new RuntimeException("Could not parse " + file + ": " + result.getProblems()); }
      CompilationUnit cu = result.getResult().get();
      LexicalPreservingPrinter.setup(cu);
      files.put(cu, file);
      for (TypeDeclaration<?> type : indexedTypes(cu))
      {
        type.getFullyQualifiedName().ifPresent(name -> typesByName.put(name, type));
        for (MethodDeclaration method : type.getMethods())
        {
          methodsByKey.put(key(type, method), method);
          methodsByName.computeIfAbsent(method.getNameAsString(), k -> new ArrayList<>()).add(method);
        }
      }
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  private void mark(List<String> entryPoints)
  {
    for (String entryPoint : entryPoints)
    {
      markEntryPoint(entryPoint);
    }
    do
    {
      drain();
    }
    while (keepVirtualMethods());
  }

  private void markEntryPoint(String entryPoint)
  {
    int paren = entryPoint.indexOf('(');
    if (paren < 0)
    {
      TypeDeclaration<?> type = typesByName.get(entryPoint.substring(0, entryPoint.lastIndexOf('.')));
      if (type == null)
      {
        report.missingEntryPoints.add(entryPoint);
        return;
      }
      keepType(type);
      return;
    }
    String head = entryPoint.substring(0, paren);
    String typeName = head.substring(0, head.lastIndexOf('.'));
    String methodName = head.substring(head.lastIndexOf('.') + 1);
    String parameters = Stream.of(entryPoint.substring(paren + 1, entryPoint.length() - 1).split(","))
        .map(String::trim).filter(p -> !p.isEmpty()).map(p -> p.substring(p.lastIndexOf('.') + 1))
        .collect(Collectors.joining(","));
    MethodDeclaration method = methodsByKey.get(typeName + "#" + methodName + "(" + parameters + ")");
    if (method == null)
    {
      report.missingEntryPoints.add(entryPoint);
      return;
    }
    keepCallable(method);
  }

  private void drain()
  {
    while (!work.isEmpty())
    {
      Node node = work.pop();
      if (node instanceof TypeDeclaration)
      {
        scanTypeLevel((TypeDeclaration<?>) node);
      }
      else
      {
        scan(node);
      }
    }
  }

  private void keepType(TypeDeclaration<?> type)
  {
    if (!keptTypes.add(type))
    { return; }
    work.push(type);
    enclosingType(type).ifPresent(this::keepType);
  }

  private void keepCallable(CallableDeclaration<?> callable)
  {
    if (!reachable.add(callable))
    { return; }
    keepType(declaringType(callable));
    work.push(callable);
  }

  private void scanTypeLevel(TypeDeclaration<?> type)
  {
    type.getAnnotations().forEach(this::scan);
    if (type instanceof ClassOrInterfaceDeclaration)
    {
      ClassOrInterfaceDeclaration c = (ClassOrInterfaceDeclaration) type;
      c.getExtendedTypes().forEach(this::scan);
      c.getTypeParameters().forEach(this::scan);
    }
    if (type instanceof NodeWithImplements)
    {
      ((NodeWithImplements<?>) type).getImplementedTypes().forEach(this::scan);
    }
    if (type instanceof EnumDeclaration)
    {
      ((EnumDeclaration) type).getEntries().forEach(this::scan);
    }
    if (type instanceof RecordDeclaration)
    {
      ((RecordDeclaration) type).getParameters().forEach(this::scan);
    }
    for (BodyDeclaration<?> member : type.getMembers())
    {
      if (member.isMethodDeclaration() || member.isTypeDeclaration())
      {
        continue;
      }
      if (member.isConstructorDeclaration())
      {
        reachable.add(member.asConstructorDeclaration());
      }
      scan(member);
    }
  }

  private void scan(Node node)
  {
    CompilationUnit cu = node.findCompilationUnit().get();
    Set<String> identifiers = new HashSet<>();
    node.findAll(SimpleName.class).forEach(n -> identifiers.add(n.getIdentifier()));
    node.findAll(Name.class).forEach(n -> identifiers.add(n.getIdentifier()));
    for (TypeDeclaration<?> type : resolveTypeNames(cu, identifiers))
    {
      keepType(type);
    }
    for (StringLiteralExpr literal : node.findAll(StringLiteralExpr.class))
    {
      TypeDeclaration<?> type = typesByName.get(literal.getValue());
      if (type != null)
      {
        keepType(type);
      }
    }
    node.findAll(MethodCallExpr.class).forEach(this::keepCall);
    node.findAll(MethodReferenceExpr.class).forEach(this::keepMethodReference);
  }

  private void keepCall(MethodCallExpr call)
  {
    String name = call.getNameAsString();
    calledNames.add(name);
    try
    {
      ResolvedMethodDeclaration resolved = call.resolve();
      String owner = resolved.declaringType().getQualifiedName();
      MethodDeclaration method = methodsByKey.get(key(resolved));
      boolean passesFunction = call.getArguments().stream()
          .anyMatch(a -> a.isLambdaExpr() || a.isMethodReferenceExpr());
      if (method != null && !passesFunction)
      {
        keepCallable(method);
      }
      else if (typesByName.containsKey(owner))
      {
        keepByName(name, call.getArguments().size(), withSupertypes(List.of(typesByName.get(owner))));
      }
    }
    catch (Exception | StackOverflowError e)
    {
      report.unresolvedCalls++;
      keepByName(name, call.getArguments().size(), possibleOwners(call, call.getScope()));
    }
  }

  private void keepMethodReference(MethodReferenceExpr reference)
  {
    String name = reference.getIdentifier();
    if ("new".equals(name))
    { return; }
    calledNames.add(name);
    try
    {
      MethodDeclaration method = methodsByKey.get(key(reference.resolve()));
      if (method != null)
      {
        keepCallable(method);
        return;
      }
    }
    catch (Exception | StackOverflowError e)
    {
      report.unresolvedCalls++;
    }
    keepByName(name, -1, possibleOwners(reference, Optional.of(reference.getScope())));
  }

  private void keepByName(String name, int arity, List<TypeDeclaration<?>> owners)
  {
    List<MethodDeclaration> candidates = new ArrayList<>();
    for (MethodDeclaration method : methodsByName.getOrDefault(name, Collections.emptyList()))
    {
      int parameters = method.getParameters().size();
      boolean varArgs = parameters > 0 && method.getParameter(parameters - 1).isVarArgs();
      if (arity < 0 || parameters == arity || (varArgs && arity >= parameters - 1))
      {
        candidates.add(method);
      }
    }
    List<MethodDeclaration> owned = candidates.stream()
        .filter(m -> owners != null && owners.stream().anyMatch(o -> o == declaringType(m)))
        .collect(Collectors.toList());
    (owned.isEmpty() ? candidates : owned).forEach(this::keepCallable);
  }

  private List<TypeDeclaration<?>> possibleOwners(Node call, Optional<Expression> scope)
  {
    CompilationUnit cu = call.findCompilationUnit().get();
    List<TypeDeclaration<?>> owners = new ArrayList<>();
    if (scope.isPresent())
    {
      Expression expression = scope.get();
      String name = expression.isNameExpr() ? expression.asNameExpr().getNameAsString()
          : expression.isTypeExpr() ? expression.asTypeExpr().getType().asString()
          : expression.isFieldAccessExpr() ? expression.toString() : null;
      if (name == null || !Character.isUpperCase(name.charAt(name.lastIndexOf('.') + 1)))
      { return null; }
      TypeDeclaration<?> type = typesByName.containsKey(name) ? typesByName.get(name)
          : resolveTypeName(cu, name.substring(name.lastIndexOf('.') + 1));
      if (type == null)
      { return null; }
      owners.add(type);
      return withSupertypes(owners);
    }
    for (Node node = call; node != null; node = node.getParentNode().orElse(null))
    {
      if (node instanceof TypeDeclaration)
      {
        owners.add((TypeDeclaration<?>) node);
      }
      if (node instanceof ObjectCreationExpr && ((ObjectCreationExpr) node).getAnonymousClassBody().isPresent())
      {
        TypeDeclaration<?> type = resolveTypeName(cu, ((ObjectCreationExpr) node).getType().getNameAsString());
        if (type == null)
        { return null; }
        owners.add(type);
      }
    }
    for (ImportDeclaration declaration : cu.getImports())
    {
      if (declaration.isStatic())
      {
        String name = declaration.getNameAsString();
        TypeDeclaration<?> type = typesByName
            .get(declaration.isAsterisk() ? name : name.substring(0, name.lastIndexOf('.')));
        if (type != null)
        {
          owners.add(type);
        }
      }
    }
    return withSupertypes(owners);
  }

  private List<TypeDeclaration<?>> withSupertypes(List<TypeDeclaration<?>> types)
  {
    List<TypeDeclaration<?>> result = new ArrayList<>(types);
    for (TypeDeclaration<?> type : types)
    {
      supertypes(type).stream().filter(s -> result.stream().noneMatch(r -> r == s)).forEach(result::add);
    }
    return result;
  }

  private boolean keepVirtualMethods()
  {
    boolean changed = false;
    for (TypeDeclaration<?> type : new ArrayList<>(keptTypes))
    {
      List<TypeDeclaration<?>> supertypes = supertypes(type);
      boolean externalSupertype = hasExternalSupertype(type);
      Set<String> inheritedAbstract = new HashSet<>();
      for (TypeDeclaration<?> supertype : supertypes)
      {
        supertype.getMethods().stream().filter(m -> m.getBody().isEmpty())
            .forEach(m -> inheritedAbstract.add(m.getNameAsString()));
      }
      for (MethodDeclaration method : type.getMethods())
      {
        if (reachable.contains(method) || method.isStatic())
        {
          continue;
        }
        String name = method.getNameAsString();
        boolean overrides = method.isAnnotationPresent("Override");
        if (method.getBody().isEmpty() || overrides || calledNames.contains(name)
            || inheritedAbstract.contains(name) || (externalSupertype && method.isPublic()))
        {
          keepCallable(method);
          changed = true;
          if (overrides)
          {
            supertypes.forEach(s -> s.getMethodsByName(name).forEach(this::keepCallable));
          }
        }
      }
    }
    return changed;
  }

  private void sweep()
  {
    for (Map.Entry<CompilationUnit, File> entry : files.entrySet())
    {
      CompilationUnit cu = entry.getKey();
      String path = relative(entry.getValue());
      if (!cu.getTypes().isEmpty() && cu.getTypes().stream().noneMatch(keptTypes::contains))
      {
        report.deletedFiles.add(path);
        continue;
      }
      for (TypeDeclaration<?> type : indexedTypes(cu))
      {
        if (!keptTypes.contains(type))
        {
          continue;
        }
        for (MethodDeclaration method : new ArrayList<>(type.getMethods()))
        {
          if (!reachable.contains(method))
          {
            method.remove();
            report.removedMethod(path);
          }
        }
        for (BodyDeclaration<?> member : new ArrayList<>(type.getMembers()))
        {
          if (member.isTypeDeclaration() && !keptTypes.contains(member.asTypeDeclaration()))
          {
            TypeDeclaration<?> nested = member.asTypeDeclaration();
            report.removedTypes.add(nested.getFullyQualifiedName().orElse(path));
            member.remove();
          }
        }
      }
    }
    for (Map.Entry<CompilationUnit, File> entry : files.entrySet())
    {
      String path = relative(entry.getValue());
      if (report.deletedFiles.contains(path))
      {
        entry.getValue().delete();
      }
      else
      {
        removeDeadImports(entry.getKey());
        FileUtils.writeFile(entry.getValue(), LexicalPreservingPrinter.print(entry.getKey()));
      }
    }
  }

  private void removeDeadImports(CompilationUnit cu)
  {
    for (ImportDeclaration declaration : new ArrayList<>(cu.getImports()))
    {
      String name = declaration.getNameAsString();
      boolean dead;
      if (declaration.isAsterisk() && !declaration.isStatic() && !typesByName.containsKey(name))
      {
        dead = isOurPackage(name) && keptTypes.stream().noneMatch(t -> packageOf(t).equals(name));
      }
      else if (declaration.isStatic() && !declaration.isAsterisk())
      {
        TypeDeclaration<?> owner = typesByName.get(name.substring(0, name.lastIndexOf('.')));
        dead = owner != null
            && (!keptTypes.contains(owner) || !hasMember(owner, name.substring(name.lastIndexOf('.') + 1)));
      }
      else
      {
        TypeDeclaration<?> type = typesByName.get(name);
        dead = type != null && !keptTypes.contains(type);
      }
      if (dead)
      {
        declaration.remove();
      }
    }
  }

  private static boolean hasMember(TypeDeclaration<?> type, String name)
  {
    for (BodyDeclaration<?> member : type.getMembers())
    {
      if ((member.isMethodDeclaration() && member.asMethodDeclaration().getNameAsString().equals(name))
          || (member.isTypeDeclaration() && member.asTypeDeclaration().getNameAsString().equals(name))
          || (member.isFieldDeclaration() && member.asFieldDeclaration().getVariables().stream()
              .anyMatch(v -> v.getNameAsString().equals(name))))
      { return true; }
    }
    return type.isEnumDeclaration()
        && type.asEnumDeclaration().getEntries().stream().anyMatch(e -> e.getNameAsString().equals(name));
  }

  private List<TypeDeclaration<?>> resolveTypeNames(CompilationUnit cu, Set<String> identifiers)
  {
    List<TypeDeclaration<?>> found = new ArrayList<>();
    for (String identifier : identifiers)
    {
      TypeDeclaration<?> type = resolveTypeName(cu, identifier);
      if (type != null)
      {
        found.add(type);
      }
    }
    for (int i = 0; i < found.size(); i++)
    {
      for (BodyDeclaration<?> member : found.get(i).getMembers())
      {
        if (member.isTypeDeclaration() && identifiers.contains(member.asTypeDeclaration().getNameAsString())
            && !found.contains(member))
        {
          found.add(member.asTypeDeclaration());
        }
      }
    }
    return found;
  }

  private TypeDeclaration<?> resolveTypeName(CompilationUnit cu, String name)
  {
    for (TypeDeclaration<?> type : indexedTypes(cu))
    {
      if (type.getNameAsString().equals(name))
      { return type; }
    }
    for (ImportDeclaration declaration : cu.getImports())
    {
      String imported = declaration.getNameAsString();
      if (!declaration.isAsterisk() && imported.endsWith("." + name) && typesByName.containsKey(imported))
      { return typesByName.get(imported); }
    }
    String samePackage = cu.getPackageDeclaration().map(p -> p.getNameAsString() + ".").orElse("") + name;
    if (typesByName.containsKey(samePackage))
    { return typesByName.get(samePackage); }
    for (ImportDeclaration declaration : cu.getImports())
    {
      String candidate = declaration.getNameAsString() + "." + name;
      if (declaration.isAsterisk() && typesByName.containsKey(candidate))
      { return typesByName.get(candidate); }
    }
    return null;
  }

  private List<TypeDeclaration<?>> supertypes(TypeDeclaration<?> type)
  {
    List<TypeDeclaration<?>> result = new ArrayList<>();
    for (ClassOrInterfaceType supertype : directSupertypes(type))
    {
      TypeDeclaration<?> resolved = resolveTypeName(type.findCompilationUnit().get(), supertype.getNameAsString());
      if (resolved != null && !result.contains(resolved))
      {
        result.add(resolved);
        supertypes(resolved).stream().filter(s -> !result.contains(s)).forEach(result::add);
      }
    }
    return result;
  }

  private boolean hasExternalSupertype(TypeDeclaration<?> type)
  {
    CompilationUnit cu = type.findCompilationUnit().get();
    return directSupertypes(type).stream().anyMatch(s -> resolveTypeName(cu, s.getNameAsString()) == null);
  }

  private static List<ClassOrInterfaceType> directSupertypes(TypeDeclaration<?> type)
  {
    List<ClassOrInterfaceType> result = new ArrayList<>();
    if (type instanceof ClassOrInterfaceDeclaration)
    {
      result.addAll(((ClassOrInterfaceDeclaration) type).getExtendedTypes());
    }
    if (type instanceof NodeWithImplements)
    {
      result.addAll(((NodeWithImplements<?>) type).getImplementedTypes());
    }
    return result;
  }

  private static List<TypeDeclaration<?>> indexedTypes(CompilationUnit cu)
  {
    List<TypeDeclaration<?>> result = new ArrayList<>();
    for (TypeDeclaration<?> type : cu.findAll(TypeDeclaration.class))
    {
      if (type.isTopLevelType() || enclosingType(type).isPresent())
      {
        result.add(type);
      }
    }
    return result;
  }

  private static Optional<TypeDeclaration<?>> enclosingType(Node node)
  {
    return node.getParentNode().filter(p -> p instanceof TypeDeclaration).map(p -> (TypeDeclaration<?>) p);
  }

  private static TypeDeclaration<?> declaringType(CallableDeclaration<?> callable)
  {
    return enclosingType(callable).get();
  }

  private boolean isOurPackage(String name)
  {
    return typesByName.keySet().stream().anyMatch(t -> t.startsWith(name + "."));
  }

  private static String packageOf(TypeDeclaration<?> type)
  {
    return type.findCompilationUnit().flatMap(CompilationUnit::getPackageDeclaration)
        .map(p -> p.getNameAsString()).orElse("");
  }

  private static String key(TypeDeclaration<?> type, MethodDeclaration method)
  {
    String parameters = method.getParameters().stream().map(TreeShaker::erase).collect(Collectors.joining(","));
    return type.getFullyQualifiedName().get() + "#" + method.getNameAsString() + "(" + parameters + ")";
  }

  private static String key(ResolvedMethodDeclaration method)
  {
    List<String> parameters = new ArrayList<>();
    for (int i = 0; i < method.getNumberOfParams(); i++)
    {
      parameters.add(erase(method.getParam(i).getType()));
    }
    return method.declaringType().getQualifiedName() + "#" + method.getName() + "(" + String.join(",", parameters)
        + ")";
  }

  private static String erase(Parameter parameter)
  {
    return erase(parameter.getType()) + (parameter.isVarArgs() ? "[]" : "");
  }

  private static String erase(Type type)
  {
    if (type.isArrayType())
    { return erase(type.asArrayType().getComponentType()) + "[]"; }
    if (type.isClassOrInterfaceType())
    { return type.asClassOrInterfaceType().getNameAsString(); }
    return type.asString();
  }

  private static String erase(ResolvedType type)
  {
    if (type.isArray())
    { return erase(type.asArrayType().getComponentType()) + "[]"; }
    if (type.isReferenceType())
    {
      String name = type.asReferenceType().getQualifiedName();
      return name.substring(name.lastIndexOf('.') + 1);
    }
    if (type.isTypeVariable())
    { return type.asTypeParameter().getName(); }
    return type.describe();
  }

  private String relative(File file)
  {
    return checkout.toPath().relativize(file.toPath()).toString();
  }

  private static List<File> javaFiles(File root)
  {
    try (Stream<Path> paths = Files.walk(root.toPath()))
    {
      return paths.filter(p -> p.toString().endsWith(".java")).map(Path::toFile).sorted()
          .collect(Collectors.toList());
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  private static <T> Set<T> identitySet()
  {
    return Collections.newSetFromMap(new IdentityHashMap<>());
  }
}
