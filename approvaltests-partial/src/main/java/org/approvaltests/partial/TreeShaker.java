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
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
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
import java.util.Comparator;
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
  private static final String                           STRING_LITERAL = "its name appears in the string literal ";
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
  private final Set<TypeDeclaration<?>>                 usedInCode     = identitySet();
  private final Map<Node, Node>                         keptBy         = new IdentityHashMap<>();
  private final Map<Node, String>                       keptWhy        = new IdentityHashMap<>();
  private Node                                          current;
  private String                                        why;
  public static ShakeReport shake(File checkout, List<String> entryPoints, List<File> thirdPartyJars)
  {
    TreeShaker shaker = new TreeShaker(checkout, thirdPartyJars);
    shaker.mark(entryPoints);
    shaker.dropEmptyStringOnlyTypes();
    shaker.explainKeptTypes();
    shaker.sweep();
    return shaker.report;
  }

  private TreeShaker(File checkout, List<File> thirdPartyJars)
  {
    this.checkout = checkout;
    ParserConfiguration typeSolverConfiguration = configuration();
    CombinedTypeSolver solver = new CombinedTypeSolver(new ReflectionTypeSolver(true));
    for (File jar : thirdPartyJars)
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
      TypeDeclaration<?> type = typesByName.containsKey(entryPoint) ? typesByName.get(entryPoint)
          : typesByName.get(entryPoint.substring(0, entryPoint.lastIndexOf('.')));
      if (type == null)
      {
        report.missingEntryPoints.add(entryPoint);
        return;
      }
      current = null;
      why = "entry point " + entryPoint;
      keepType(type);
      return;
    }
    String head = entryPoint.substring(0, paren);
    String typeName = head.substring(0, head.lastIndexOf('.'));
    String methodName = head.substring(head.lastIndexOf('.') + 1);
    List<String> parameters = Stream.of(entryPoint.substring(paren + 1, entryPoint.length() - 1).split(","))
        .map(String::trim).filter(p -> !p.isEmpty()).map(p -> p.substring(p.lastIndexOf('.') + 1))
        .collect(Collectors.toList());
    TypeDeclaration<?> owner = typesByName.get(typeName);
    if (owner != null && methodName.equals(owner.getNameAsString()))
    {
      current = null;
      why = "entry point " + entryPoint;
      keepType(owner);
      return;
    }
    MethodDeclaration method = methodsByKey
        .get(typeName + "#" + methodName + "(" + String.join(",", parameters) + ")");
    if (method == null && owner != null)
    {
      method = owner.getMethodsByName(methodName).stream().filter(m -> matchesErased(m, parameters)).findFirst()
          .orElse(null);
    }
    if (method == null)
    {
      report.missingEntryPoints.add(entryPoint);
      return;
    }
    current = null;
    why = "entry point " + entryPoint;
    keepCallable(method);
  }

  private static boolean matchesErased(MethodDeclaration method, List<String> parameters)
  {
    if (method.getParameters().size() != parameters.size())
    { return false; }
    Set<String> typeVariables = new HashSet<>();
    method.getTypeParameters().forEach(t -> typeVariables.add(t.getNameAsString()));
    for (Node n = method.getParentNode().orElse(null); n != null; n = n.getParentNode().orElse(null))
    {
      if (n instanceof ClassOrInterfaceDeclaration)
      {
        ((ClassOrInterfaceDeclaration) n).getTypeParameters().forEach(t -> typeVariables.add(t.getNameAsString()));
      }
    }
    for (int i = 0; i < parameters.size(); i++)
    {
      String declared = erase(method.getParameter(i));
      String expected = parameters.get(i);
      String declaredBase = declared.replace("[]", "");
      boolean sameArrays = count(declared, "[]") == count(expected, "[]");
      if (!declared.equals(expected) && !(typeVariables.contains(declaredBase) && sameArrays))
      { return false; }
    }
    return true;
  }

  private static int count(String text, String part)
  {
    return (text.length() - text.replace(part, "").length()) / part.length();
  }

  private void drain()
  {
    while (!work.isEmpty())
    {
      Node node = work.pop();
      current = node;
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
    if (why == null || !why.startsWith(STRING_LITERAL))
    {
      usedInCode.add(type);
    }
    if (!keptTypes.add(type))
    { return; }
    record(type);
    work.push(type);
    Optional<TypeDeclaration<?>> enclosing = enclosingType(type);
    if (enclosing.isPresent())
    {
      because(type, "it encloses a kept nested type", () -> keepType(enclosing.get()));
    }
  }

  private void keepCallable(CallableDeclaration<?> callable)
  {
    if (!reachable.add(callable))
    { return; }
    record(callable);
    because(callable, "it declares a kept method", () -> keepType(declaringType(callable)));
    work.push(callable);
  }

  private void record(Node node)
  {
    keptBy.put(node, current);
    keptWhy.put(node, why);
  }

  private void because(Node source, String reason, Runnable keep)
  {
    Node previousNode = current;
    String previousWhy = why;
    current = source;
    why = reason;
    keep.run();
    current = previousNode;
    why = previousWhy;
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
      why = "referenced by " + describeMember(member) + " (fields, initializers and constructors of kept types are always kept)";
      scanReferences(member);
    }
  }

  private void scan(Node node)
  {
    why = node instanceof CallableDeclaration ? "referenced in its body" : "referenced in its declaration";
    scanReferences(node);
  }

  private void scanReferences(Node node)
  {
    String referenced = why;
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
        why = STRING_LITERAL + literal;
        keepType(type);
      }
    }
    for (MethodCallExpr call : node.findAll(MethodCallExpr.class))
    {
      why = referenced.replace("referenced", "called") + ": " + call;
      keepCall(call);
    }
    for (MethodReferenceExpr reference : node.findAll(MethodReferenceExpr.class))
    {
      why = referenced.replace("referenced", "method reference") + ": " + reference;
      keepMethodReference(reference);
    }
    why = referenced;
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
        why += passesFunction ? " (passes a lambda, so every overload with this arity is kept)"
            : " (resolved method not found, kept by name)";
        keepByName(name, call.getArguments().size(), withSupertypes(List.of(typesByName.get(owner))));
      }
    }
    catch (Exception | StackOverflowError e)
    {
      report.unresolvedCalls++;
      why += " (could not resolve, kept by name and arity)";
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
    why += " (could not resolve, kept by name)";
    keepByName(name, -1, possibleOwners(reference, Optional.of(reference.getScope())));
  }

  private void keepByName(String name, int arity, List<TypeDeclaration<?>> owners)
  {
    if (owners != null && owners.isEmpty())
    { return; }
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
      String simpleName = name == null ? "" : name.substring(name.lastIndexOf('.') + 1);
      boolean looksLikeType = !simpleName.isEmpty() && Character.isUpperCase(simpleName.charAt(0))
          && !simpleName.equals(simpleName.toUpperCase());
      if (!looksLikeType)
      { return null; }
      TypeDeclaration<?> type = typesByName.containsKey(name) ? typesByName.get(name) : resolveTypeName(cu, simpleName);
      if (type == null)
      { return owners; }
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
        String reason = method.getBody().isEmpty() ? "it is abstract"
            : overrides ? "it is @Override"
            : inheritedAbstract.contains(name) ? "it implements an inherited abstract method"
            : calledNames.contains(name) ? "a method named " + name + "() is called somewhere"
            : externalSupertype && method.isPublic() ? "its class has a third-party supertype" : null;
        if (reason != null)
        {
          current = type;
          why = reason + " (instance methods of kept types)";
          keepCallable(method);
          changed = true;
          if (overrides)
          {
            current = method;
            why = "it is overridden by a kept @Override method";
            supertypes.forEach(s -> s.getMethodsByName(name).forEach(this::keepCallable));
          }
        }
      }
    }
    return changed;
  }

  private void dropEmptyStringOnlyTypes()
  {
    for (TypeDeclaration<?> type : new ArrayList<>(keptTypes))
    {
      boolean empty = type.getMembers().stream()
          .allMatch(m -> (m.isMethodDeclaration() && !reachable.contains(m.asMethodDeclaration()))
              || (m.isTypeDeclaration() && !keptTypes.contains(m.asTypeDeclaration())));
      if (!usedInCode.contains(type) && empty)
      {
        keptTypes.remove(type);
        report.droppedStringOnlyTypes.add(type.getFullyQualifiedName().orElse(type.getNameAsString()));
      }
    }
  }

  private void explainKeptTypes()
  {
    for (TypeDeclaration<?> type : keptTypes)
    {
      report.keptBecause.put(type.getFullyQualifiedName().orElse(type.getNameAsString()), explain(type));
    }
  }

  private List<String> explain(Node node)
  {
    List<String> chain = new ArrayList<>();
    Set<Node> seen = identitySet();
    for (Node n = node; n != null && seen.add(n); n = keptBy.get(n))
    {
      chain.add(describe(n) + " <- " + keptWhy.get(n));
    }
    return chain;
  }

  private static String describe(Node node)
  {
    if (node instanceof TypeDeclaration)
    { return ((TypeDeclaration<?>) node).getFullyQualifiedName().orElse("?"); }
    if (node instanceof CallableDeclaration)
    {
      CallableDeclaration<?> callable = (CallableDeclaration<?>) node;
      String parameters = callable.getParameters().stream().map(TreeShaker::erase).collect(Collectors.joining(", "));
      return enclosingType(callable).flatMap(TypeDeclaration::getFullyQualifiedName).orElse("?") + "."
          + callable.getNameAsString() + "(" + parameters + ")";
    }
    return node.getClass().getSimpleName();
  }

  private static String describeMember(BodyDeclaration<?> member)
  {
    if (member.isFieldDeclaration())
    { return "field " + member.asFieldDeclaration().getVariables().stream().map(v -> v.getNameAsString())
        .collect(Collectors.joining(", ")); }
    if (member.isConstructorDeclaration())
    { return "constructor " + describe(member); }
    return member.getClass().getSimpleName();
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
    for (String root : SOURCE_ROOTS)
    {
      deleteEmptyDirectories(new File(checkout, root));
    }
  }

  private void deleteEmptyDirectories(File root)
  {
    try (Stream<Path> paths = Files.walk(root.toPath()))
    {
      List<File> directories = paths.map(Path::toFile).filter(File::isDirectory).filter(d -> !d.equals(root))
          .sorted(Comparator.comparing(File::getPath).reversed()).collect(Collectors.toList());
      for (File directory : directories)
      {
        String[] contents = directory.list();
        if (contents != null && contents.length == 0 && directory.delete())
        {
          report.deletedDirectories.add(relative(directory));
        }
      }
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
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
      if (dead || !isUsed(cu, declaration))
      {
        declaration.remove();
      }
    }
  }

  private static boolean isUsed(CompilationUnit cu, ImportDeclaration declaration)
  {
    if (declaration.isAsterisk())
    { return true; }
    String name = declaration.getName().getIdentifier();
    return cu.getTypes().stream().anyMatch(type -> type.findAll(SimpleName.class).stream()
        .anyMatch(n -> n.getIdentifier().equals(name))
        || type.findAll(Name.class).stream().anyMatch(n -> n.getIdentifier().equals(name)));
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
