package org.approvaltests;

import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.utils.SourceRoot;
import com.spun.util.FormattedException;
import com.spun.util.StringUtils;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.List;

public class ParsingFilesTest
{
  @Test
  void testWithoutGenerics()
  {
    var expected = """
      T[] -> Object[]
      IN3[] -> Object[]
      List<String> -> List
      IN[][] -> Object[][]
      """;
    String[] cases = {"T[]", "IN3[]", "List<String>", "IN[][]"};
    Approvals.verifyAll("", cases, c -> String.format("%s -> %s", c, removeGenerics(c)), new Options().inline(expected));
  }
  @Test
  public void getLineNumberOfThisMethod() throws Exception
  {
    var expected = """
        (line 5,col 3)-(line 7,col 3)
        """;
    Method method = XmlApprovals.class.getMethod("verify", String.class);
    Range r = getMethodLines(method);
    Approvals.verify(r, new Options().inline(expected));
  }
  public static Range getMethodLines(Method method)
  {
    CompilationUnit compilationUnit = parseJavaFile(method);
    List<MethodDeclaration> methods = compilationUnit.getClassByName(method.getDeclaringClass().getSimpleName())
        .get().getMethods();
    for (MethodDeclaration methodDeclaration : methods)
    {
      if (isMethodDeclarationEqualToMethod(methodDeclaration, method))
      { return methodDeclaration.getRange().get(); }
    }
    throw new FormattedException("Could not find method %s(%s) in %s", method.getName(),
        DocumentHelpersTest.showParameters(method), method.getDeclaringClass());
  }
  private static CompilationUnit parseJavaFile(Method method)
  {
    File filePath = getSourceFile(method);
    String baseDir = StringUtils.removeFromEnd(System.getProperty("user.dir"), "-tests") + "/src/main/java/";
    SourceRoot sourceRoot = new SourceRoot(Paths.get(baseDir));
    String packageName = method.getDeclaringClass().getPackageName();
    String name = filePath.getName();
    return sourceRoot.parse(packageName, name);
  }
  private static File getSourceFile(Method method)
  {
    String baseDir = StringUtils.removeFromEnd(System.getProperty("user.dir"), "-tests");
    String className = method.getDeclaringClass().getName();
    String relativePath = "src/main/java/" + className.replace('.', '/') + ".java";
    return new File(baseDir, relativePath);
  }
  public static boolean isMethodDeclarationEqualToMethod(MethodDeclaration methodDeclaration, Method method)
  {
    String currentMethod = String.format("%s(%s)", method.getName(), DocumentHelpersTest.showParameters(method));
    if (!methodDeclaration.getNameAsString().equals(method.getName()))
    { return false; }
    List<Class<?>> compiledMethodParameters = List.of(method.getParameterTypes());
    NodeList<Parameter> parsedMethodParameters = methodDeclaration.getParameters();
    if (compiledMethodParameters.size() != parsedMethodParameters.size())
    { return false; }
    for (int i = 0; i < compiledMethodParameters.size(); i++)
    {
      String compileName = compiledMethodParameters.get(i).getSimpleName();
      String parseName = parsedMethodParameters.get(i).getTypeAsString();
      if (parsedMethodParameters.get(i).isVarArgs()) {
        parseName += "[]";
      }
      String withoutGenerics = removeGenerics(parseName);
      if (!compileName.equals(withoutGenerics))
      { return false; }
    }
    return true;
  }
  private static String removeGenerics(String parseName)
  {
    String without = parseName.replaceAll("<.*>", "");
    if (without.endsWith("[][]"))
    {
      String name = StringUtils.removeFromEnd(without, "[][]");
      if (name.length() <= 3)
      { return "Object[][]"; }
    }
    else if (without.endsWith("[]"))
    {
      String name = StringUtils.removeFromEnd(without, "[]");
      if (name.length() <= 3)
      { return "Object[]"; }
    }
    return without;
  }
}
