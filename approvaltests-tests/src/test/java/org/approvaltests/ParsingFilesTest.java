package org.approvaltests;

import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;
import com.spun.util.StringUtils;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Paths;

public class ParsingFilesTest
{
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
  private Range getMethodLines(Method method)
  {
    CompilationUnit compilationUnit = parseJavaFile(method);
    return compilationUnit.getClassByName("XmlApprovals").get().getMember(0).getRange().get();
  }
  private CompilationUnit parseJavaFile(Method method)
  {
    File filePath = getSourceFile(method);
    String baseDir = StringUtils.removeFromEnd(System.getProperty("user.dir"), "-tests") + "/src/main/java/";
    SourceRoot sourceRoot = new SourceRoot(Paths.get(baseDir));
    String packageName = method.getDeclaringClass().getPackageName();
    String name = filePath.getName();
    return sourceRoot.parse(packageName, name);
  }
  private File getSourceFile(Method method)
  {
    String baseDir = StringUtils.removeFromEnd(System.getProperty("user.dir"), "-tests");
    String className = method.getDeclaringClass().getName();
    String relativePath = "src/main/java/" + className.replace('.', '/') + ".java";
    return new File(baseDir, relativePath);
  }
}
