package org.approvaltests;

import com.spun.util.FormattedException;
import com.spun.util.ObjectUtils;
import com.spun.util.StringUtils;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lambda.functions.Functions;
import org.lambda.query.Query;
import org.lambda.query.Queryable;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;

public class DocumentHelpersTest
{
  @Test
  void listAllVerifyFunctions()
  {
    Queryable<Method> methods = getAllVerifyFunctionsWithOptions(OptionsTest.getApprovalClasses());
    Queryable<String> lines = methods.select(m -> String.format("%s. [%s ](%s) (%s)",
        m.getDeclaringClass().getSimpleName(), m.getName(), getLink(m), showParameters(m)))
        .orderBy(s -> s.replaceAll("#L\\d+-L\\d+", ""));
    Approvals.verifyAll("", lines, l -> String.format(" * %s  ", l), new Options().forFile().withExtension(".md"));
  }
  @Test
  public void testLineNumberOfThisMethod() throws NoSuchMethodException
  {
    Method method = DocumentHelpersTest.class.getMethod("testLineNumberOfThisMethod");
    CtMethod m = getMethodX(method);
    Assertions.assertEquals(33.0, getLineNumber(m), 1.01);
  }
  // why is this calculation of the modifier necessary?
  public static int getLineNumber(CtMethod m)
  {
    int modifier = 0;
    String simpleName = m.getDeclaringClass().getSimpleName();
    if (simpleName.equals("DocumentHelpersTest"))
    {
      modifier = -2;
    }
    else if (simpleName.equals("Approvals"))
    {
      modifier = 3;
    }
    else if (simpleName.equals("CombinationApprovals"))
    {
      modifier = 3;
    }
    else if (simpleName.equals("AwtApprovals"))
    {
      modifier = 12;
    }
    else if (simpleName.equals("JsonApprovals"))
    {
      modifier = 2;
    }
    else if (simpleName.equals("JsonJacksonApprovals"))
    {
      modifier = -2;
    }
    else if (simpleName.equals("JsonXstreamApprovals"))
    {
      modifier = -2; // blind guess / copy&paste from above
    }
    else if (simpleName.equals("VelocityApprovals"))
    {
      modifier = 2;
    }
    else
    {
      throw new FormattedException("Unknown modifier (%s) for class %s", modifier, simpleName);
    }
    return m.getMethodInfo().getLineNumber(0) + modifier;
  }
  @Test
  void testLineNumbers()
  {
    getLink(Query.first(Approvals.class.getMethods(),
        m -> m.getName().equals("verifyAll") && m.getParameterTypes()[0].equals(Object[].class)));
  }
  private String showParameters(Method m)
  {
    return StringUtils.join(Query.select(m.getParameters(), p -> String.format("%s", p.getType().getSimpleName())),
        ",");
  }
  public static String getLink(Method m)
  {
    String baseUrl = "https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java";
    String file = m.getDeclaringClass().getName().replace('.', '/') + ".java";
    CtMethod methodX = getMethodX(m);
    int start = getLineNumber(methodX);
    int end = start + 3;
    return String.format("%s/%s#L%s-L%s", baseUrl, file, start, end);
  }
  private static CtMethod getMethodX(Method method)
  {
    ClassPool pool = ClassPool.getDefault();
    CtClass cc = ObjectUtils.throwAsError(() -> pool.get(method.getDeclaringClass().getName()));
    Queryable<CtMethod> part1 = Query.where(cc.getDeclaredMethods(), m -> m.getName().equals(method.getName()));
    Queryable<CtMethod> part2 = part1
        .where(Functions.unchecked(m -> m.getParameterTypes().length == method.getParameterTypes().length));
    Queryable<CtMethod> part3 = part2
        .where(Functions.unchecked(m -> Query.all(m.getParameterTypes(), p -> Query.any(method.getParameterTypes(),
            Functions.unchecked(mp -> mp.getSimpleName().equals(p.getSimpleName()))))));
    CtMethod methodX = part3.first();
    if (methodX == null)
    {
      Queryable<String> select = part2.select(Functions
          .unchecked(m -> Query.select(m.getParameterTypes(), Functions.unchecked(p -> p.getName())).toString()));
      String originalMethod = Query.select(method.getParameterTypes(), p -> p.getSimpleName()).toString();
      String foo = "[Ljava.lang.Object;";
      throw new FormattedException("Couldn't find method for %s", method.getName());
    }
    return methodX;
  }
  private static String getRegexForParameter(Method m)
  {
    // https://github.com/jboss-javassist/javassist
    //        Class<?> parameterType = m.getParameterTypes()[0];
    Type typeParameters = m.getGenericParameterTypes()[0];
    String name = Query.last(StringUtils.split(typeParameters.getTypeName(), "."));
    return name.replaceAll("\\[\\]", "");
  }
  private static int countNewLines(String code, int untilIndex)
  {
    return code.substring(0, untilIndex).split("\n").length;
  }
  private boolean isStartOfMethod(Method m, String line)
  {
    String pattern = "void " + m.getName() + "(" + m.getParameterTypes()[0].getSimpleName();
    return line.contains(pattern) && line.contains(", Options");
  }
  private Queryable<Method> getAllVerifyFunctionsWithOptions(List<Class<?>> approvalClasses)
  {
    Queryable<Method> methods = new Queryable<>(Method.class);
    for (Class c : approvalClasses)
    {
      Queryable<Method> declaredMethods = Queryable.as(c.getDeclaredMethods());
      Queryable<Method> methodList = declaredMethods.where(m -> m.getName().startsWith("verify")
          && Modifier.isPublic(m.getModifiers()) && !m.isAnnotationPresent(Deprecated.class));
      List<Method> methodsWithOptions = methodList.where(m -> !OptionsTest.isOptionsPresent(m));
      methods.addAll(methodsWithOptions);
    }
    return methods;
  }
}
