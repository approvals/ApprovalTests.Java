package org.approvaltests.namer;

import com.spun.util.FormattedException;
import com.spun.util.ObjectUtils;
import com.spun.util.ThreadUtils;
import com.spun.util.io.StackElementSelector;
import com.spun.util.tests.TestUtils;
import org.approvaltests.integrations.junit5.JUnitUtils;
import org.lambda.query.Queryable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AttributeStackSelector implements StackElementSelector
{
  public static String[]                    classNames = {"org.testng.annotations.Test",
                                                          "org.junit.Test",
                                                          "org.junit.jupiter.api.Test",
                                                          "org.junit.jupiter.api.TestFactory",
                                                          "org.junit.jupiter.api.RepeatedTest",
                                                          "org.junit.jupiter.params.ParameterizedTest"};
  private List<Class<? extends Annotation>> attributes;
  public AttributeStackSelector()
  {
    attributes = getAvailableAttributes();
  }
  private List<Class<? extends Annotation>> getAvailableAttributes()
  {
    ArrayList<Class<? extends Annotation>> attributes = new ArrayList<Class<? extends Annotation>>();
    for (String className : classNames)
    {
      Class<? extends Annotation> clazz = loadClass(className);
      if (clazz != null)
      {
        attributes.add(clazz);
      }
    }
    return attributes;
  }
  public static Class<? extends Annotation> loadClass(String className)
  {
    Class<? extends Annotation> clazz = null;
    try
    {
      clazz = (Class<? extends Annotation>) ObjectUtils.loadClass(className);
    }
    catch (Throwable e)
    {
      clazz = null;
    }
    return clazz;
  }
  @Override
  public StackTraceElement selectElement(StackTraceElement[] trace)
  {
    boolean inTestCase = false;
    for (int i = 0; i < trace.length; i++)
    {
      if (isTestCase(trace[i]))
      {
        inTestCase = true;
      }
      else if (inTestCase)
      { return trace[i - 1]; }
    }
    throw new RuntimeException(
        "Could not find Junit/TestNg TestCase you are running, supported frameworks: Junit3, Junit4, Junit5, TestNg");
  }
  private boolean isTestCase(StackTraceElement element)
  {
    String fullClassName = element.getClassName();
    Class<?> clazz = loadClass(fullClassName);
    if (clazz == null)
    { return false; }
    if (isJunit3Test(clazz))
    { return true; }
    if (isTestAttribute(clazz, TestUtils.unrollLambda(element.getMethodName())))
    { return true; }
    if (isTestableMethod(element))
    { return true; }
    return false;
  }
  public static Boolean isJunit5Present = null;
  public static boolean isTestableMethod(StackTraceElement element)
  {
    if (isJunit5Present == null)
    {
      try
      {
        Class.forName("org.junit.platform.commons.annotation.Testable");
        isJunit5Present = true;
      }
      catch (ClassNotFoundException e)
      {
        isJunit5Present = false;
      }
    }
    if (!isJunit5Present)
    { return false; }
    return JUnitUtils.isTestableMethodForJunit(element);
  }
  private boolean isJunit3Test(Class<?> clazz)
  {
    Class<?> testcase = loadClass("junit.framework.TestCase");
    return testcase != null && ObjectUtils.isThisInstanceOfThat(clazz, testcase);
  }
  private boolean isTestAttribute(Class<?> clazz, String methodName)
  {
    Queryable<Method> methods = getMethodsByName(clazz, methodName);
    Class<? extends Annotation> attribute = Queryable.as(attributes) //
        .first(a -> methods.any(m -> m.isAnnotationPresent(a)));
    checkConditionsForAttribute(attribute);
    return attribute != null;
  }
  // TODO: clean this up, should be pluggable
  private static void checkConditionsForAttribute(Class<? extends Annotation> attribute)
  {
    if (attribute == null)
    { return; }
    if ("org.junit.jupiter.api.TestFactory".equals(attribute.getName()))
    {
      if (!isDynamicWrapperPresent())
      {
        String helpMessage = "When using dynamic tests and Approvals, Instead use:  \n"
            + "  org.approvaltests.integrations.junit5.JupiterApprovals.dynamicTest(String, Executable)\n"
            + " More at: https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/how_to/UseTestFactory.md";
        throw new FormattedException(helpMessage);
      }
    }
  }
  private static boolean isDynamicWrapperPresent()
  {
    StackTraceElement[] stackTrace = ThreadUtils.getStackTrace();
    return Queryable.as(stackTrace)
        .any(stackTraceElement -> "org.approvaltests.integrations.junit5.JupiterApprovals"
            .equals(stackTraceElement.getClassName()));
  }
  public static Queryable<Method> getMethodsByName(Class<?> clazz, String methodName)
  {
    try
    {
      return Queryable.of(clazz.getDeclaredMethods()) //
          .where(m -> m.getName().equals(methodName));
    }
    catch (Throwable e)
    {
      return new Queryable<>();
    }
  }
  @Override
  public void increment()
  {
    //ignore
  }
}
