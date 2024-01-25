package org.approvaltests;

import com.github.javaparser.Range;
import com.spun.util.StringUtils;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;
import org.lambda.functions.Function1;
import org.lambda.query.Query;
import org.lambda.query.Queryable;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.List;

public class DocumentHelpersTest {
  @Test
  void listAllVerifyFunctions() {
    Queryable<Method> methods = getAllVerifyFunctionsWithOptions(OptionsTest.getApprovalClasses());
    Queryable<String> lines = methods.select(m -> String.format("%s. [%s ](%s) (%s)",
                    m.getDeclaringClass().getSimpleName(), m.getName(), getLink(m), showParametersExceptOptions(m)))
            .orderBy(s -> s.replaceAll("#L\\d+-L\\d+", ""));
    Approvals.verifyAll("", lines, l -> String.format(" * %s  ", l), new Options().forFile().withExtension(".md"));
  }

  @Test
  void testLineNumbers() {
    var expected = """
            https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L98-L101
            """;
    String verifyAll = getLink(Query.first(Approvals.class.getMethods(),
            m -> m.getName().equals("verifyAll") && m.getParameterTypes()[0].equals(Object[].class)));
    Approvals.verify(verifyAll, new Options().inline(expected));
  }

  public static String showParameters(Method m) {
    return showParametersExcept(m, p -> true);
  }
  public static String showParametersExceptOptions(Method m)
  {
      return showParametersExcept(m, p -> !p.getType().getSimpleName().equals("Options"));
  }

  private static String showParametersExcept(Method m, Function1<Parameter, Boolean> filter) {
    Queryable<Parameter> withoutOptions = Query.where(m.getParameters(), filter);
    return StringUtils.join(withoutOptions.select(p -> String.format("%s", p.getType().getSimpleName())), ",");
  }

  public static String getLink(Method m)
  {
    String baseUrl = "https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java";
    String file = m.getDeclaringClass().getName().replace('.', '/') + ".java";
    Range methodLines = ParsingFilesTest.getMethodLines(m);
    int start = methodLines.begin.line;
    int end = methodLines.end.line;
    return String.format("%s/%s#L%s-L%s", baseUrl, file, start, end);
  }

  private Queryable<Method> getAllVerifyFunctionsWithOptions(List<Class<?>> approvalClasses)
  {
    Queryable<Method> methods = new Queryable<>(Method.class);
    for (Class c : approvalClasses)
    {
      Queryable<Method> declaredMethods = Queryable.as(c.getDeclaredMethods());
      Queryable<Method> methodList = declaredMethods.where(m -> m.getName().startsWith("verify")
          && Modifier.isPublic(m.getModifiers()) && !m.isAnnotationPresent(Deprecated.class));
      List<Method> methodsWithOptions = methodList.where(m -> OptionsTest.isOptionsPresent(m));
      methods.addAll(methodsWithOptions);
    }
    return methods;
  }
}