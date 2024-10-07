package org.approvaltests;

import com.github.javaparser.Range;
import com.spun.util.StringUtils;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;
import org.lambda.query.Query;
import org.lambda.query.Queryable;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.List;

public class DocumentHelpersTest
{
  @Test
  void listAllVerifyFunctions()
  {
    ParsingFilesTest.addApprovalTestPath();
    Queryable<Method> methods = getAllVerifyFunctionsWithOptions(OptionsTest.getApprovalClasses());
    Queryable<String> lines = methods.select(m -> String.format("%s. [%s ](%s) (%s)",
        m.getDeclaringClass().getSimpleName(), m.getName(), getLink(m), showParametersWithGrayedOutOptions(m)))
        .orderBy(s -> s.replaceAll("#L\\d+-L\\d+", ""));
    Approvals.verifyAll("", lines, l -> String.format(" * %s  ", l), new Options().forFile().withExtension(".md"));
  }
  @Test
  void testLineNumbers()
  {
    ParsingFilesTest.addApprovalTestPath();
    var expected = """
        https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L103-L106
        """;
    String verifyAll = getLink(Queryable.as(Approvals.class.getMethods()).orderBy(m -> m.getParameterCount())
        .first(m -> m.getName().equals("verifyAll") && m.getParameterTypes()[0].equals(Object[].class)));
    Approvals.verify(verifyAll, new Options().inline(expected));
  }
  public static String showParameters(Method m)
  {
    Queryable<Parameter> withoutOptions = Query.where(m.getParameters(), p -> true);
    return StringUtils.join(withoutOptions.select(p1 -> String.format("%s", p1.getType().getSimpleName())), ",");
  }
  public static String showParametersWithGrayedOutOptions(Method m)
  {
    Queryable<Parameter> withoutOptions = Queryable.as(m.getParameters());
    return StringUtils.join(withoutOptions.select(DocumentHelpersTest::formatTypesWithGrayedOutOptions), ", ");
  }
  private static String formatTypesWithGrayedOutOptions(Parameter p1)
  {
    String simpleName = p1.getType().getSimpleName();
    return simpleName.equals("Options") ? "$\\color{#AAA}{\\textsf{Options}}$" : simpleName;
  }
  public static String getLink(Method m)
  {
    String baseUrl = "https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java";
    String file = m.getDeclaringClass().getName().replace('.', '/') + ".java";
    Range methodLines = ParserUtilities.getLineNumbersForMethod(m);
    int start = methodLines.begin.line;
    int end = methodLines.end.line;
    return String.format("%s/%s#L%s-L%s", baseUrl, file, start, end);
  }
  private Queryable<Method> getAllVerifyFunctionsWithOptions(List<Class<?>> approvalClasses)
  {
    Queryable<Method> methods = new Queryable<>(Method.class);
    for (Class<?> c : approvalClasses)
    {
      Queryable<Method> declaredMethods = Queryable.as(c.getDeclaredMethods());
      Queryable<Method> methodList = declaredMethods.where(m -> m.getName().startsWith("verify")
          && Modifier.isPublic(m.getModifiers()) && !m.isAnnotationPresent(Deprecated.class));
      List<Method> methodsWithOptions = methodList.where(OptionsTest::isOptionsPresent);
      methods.addAll(methodsWithOptions);
    }
    return methods;
  }
}