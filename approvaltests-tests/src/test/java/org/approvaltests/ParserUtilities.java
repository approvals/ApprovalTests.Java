package org.approvaltests;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.utils.SourceRoot;
import com.spun.util.FormattedException;
import org.lambda.query.Query;

import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ParserUtilities
{
  public static final List<String> SOURCE_PATHS = new ArrayList<>();
  static
  {
    SOURCE_PATHS.add("src/main/java");
    SOURCE_PATHS.add("src/test/java");
  }
  public static Range getLineNumbersForMethod(Method method)
  {
    MethodDeclaration methodDeclaration = getMethodDeclaration(method);
    return methodDeclaration.getRange().get();
  }
  public static MethodDeclaration getMethodDeclaration(Method method)
  {
    CompilationUnit cu = getCompilationUnit(method);
    MethodDeclaration methodDeclaration = cu.findFirst(MethodDeclaration.class, md -> findMethod(method, md))
        .orElse(null);
    if (methodDeclaration == null)
    {
      throw new FormattedException("Method Not Found:\n%s.%s(params...)",
          method.getDeclaringClass().getSimpleName(), method.getName());
    }
    return methodDeclaration;
  }
  private static boolean findMethod(Method compiledMethod, MethodDeclaration parsedMethod)
  {
    if (!parsedMethod.getNameAsString().equals(compiledMethod.getName()))
    { return false; }
    List<String> compiledParameterTypes = Query.select(compiledMethod.getParameterTypes(), Class::getSimpleName);
    NodeList<Parameter> parsedParameterTypes = parsedMethod.getParameters();
    if (parsedParameterTypes.size() != compiledParameterTypes.size())
    { return false; }
    NodeList<TypeParameter> typeParameters = parsedMethod.getTypeParameters();
    for (int i = 0; i < parsedParameterTypes.size(); i++)
    {
      Parameter parsed = parsedParameterTypes.get(i);
      String compiledType = compiledParameterTypes.get(i);
      if (!isCompiledTypeSameAsParsedType(parsed, compiledType, typeParameters))
      { return false; }
    }
    return true;
  }
  public static boolean isCompiledTypeSameAsParsedType(Parameter parsed, String compiledType,
      NodeList<TypeParameter> typeParameters)
  {
    // Get the parsed parameter's type as a string
    return compiledType.equals(convertParsedParameterToCompiledTypeSimpleName(parsed, typeParameters));
  }
  public static CompilationUnit getCompilationUnit(Method method)
  {
    // Parsing the source file
    CompilationUnit cu = null;
    ParseProblemException parseException = null;
    for (String sourceRootPath : SOURCE_PATHS)
    {
      SourceRoot sourceRoot = new SourceRoot(Paths.get(sourceRootPath));
      try
      {
        cu = sourceRoot.parse(method.getDeclaringClass().getPackageName(),
            method.getDeclaringClass().getSimpleName() + ".java");
        break;
      }
      catch (ParseProblemException e)
      {
        parseException = e;
      }
    }
    if (cu == null && parseException != null)
    {
      throw new RuntimeException("Error parsing the source file: " + parseException.getMessage(), parseException);
    }
    return cu;
  }
  public static String convertParsedParameterToCompiledTypeSimpleName(Parameter parameter,
      List<TypeParameter> methodTypeParameters)
  {
    Type type = parameter.getType();
    // Handle varargs, which are syntactically similar to arrays in the type system
    boolean isVarArg = parameter.isVarArgs();
    // Check if the parameter type is a generic type parameter
    if (methodTypeParameters.stream().anyMatch(tp -> type.toString().startsWith(tp.getNameAsString())) || isVarArg)
    {
      // Adjust for varargs or regular arrays
      long arrayCount = type.toString().chars().filter(ch -> ch == '[').count() + (isVarArg ? 1 : 0); // Add an extra array level for varargs
      String baseType = "Object";
      // Construct the array representation if needed
      String arraySuffix = "";
      for (int i = 0; i < arrayCount; i++)
      {
        arraySuffix += "[]";
      }
      return baseType + arraySuffix;
    }
    else
    {
      // For non-generic types, return the type name directly, removing generics information
      String typeName = type.toString();
      int genericMarkerIndex = typeName.indexOf('<');
      if (genericMarkerIndex != -1)
      {
        typeName = typeName.substring(0, genericMarkerIndex);
      }
      return typeName;
    }
  }
}
