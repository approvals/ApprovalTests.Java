package org.approvaltests.namer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.lambda.functions.Function0;

import com.spun.util.FormattedException;
import com.spun.util.ObjectUtils;
import com.spun.util.io.StackElementSelector;
import com.spun.util.tests.TestUtils;

public class AttributeStackSelector implements StackElementSelector
{
  public static String                      classNames[] = {"org.testng.annotations.Test",
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
  private Class<? extends Annotation> loadClass(String className)
  {
    Class<? extends Annotation> clazz = null;
    try
    {
      clazz = (Class<? extends Annotation>) ObjectUtils.loadClass(className);
    }
    catch (ClassNotFoundException e)
    {
      clazz = null;
    }
    return clazz;
  }
  @Override
  public StackTraceElement selectElement(StackTraceElement[] trace) throws Exception
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
  private boolean isTestCase(StackTraceElement element) throws ClassNotFoundException
  {
    String fullClassName = element.getClassName();
    Class<?> clazz = loadClass(fullClassName);
    if (clazz == null)
    { return false; }
    if (isJunit3Test(clazz))
    { return true; }
    return isTestAttribute(clazz, TestUtils.unrollLambda(element.getMethodName()));
  }
  private boolean isJunit3Test(Class<?> clazz)
  {
    Class<?> testcase = loadClass("junit.framework.TestCase");
    return testcase != null && ObjectUtils.isThisInstanceOfThat(clazz, testcase);
  }
  private boolean isTestAttribute(Class<?> clazz, String methodName)
      throws ClassNotFoundException, SecurityException
  {
    List<Method> methods = getMethodsByName(clazz, methodName);
    if (methods.isEmpty())
    { return false; }
    for (Method method : methods)
    {
      for (Class<? extends Annotation> attribute : attributes)
      {
        if (method.isAnnotationPresent(attribute))
        { return getConditionsForAttribute(attribute).call(); }
      }
    }
    return false;
  }
  private Function0<Boolean> getConditionsForAttribute(Class<? extends Annotation> attribute)
  {
    if ("org.junit.jupiter.api.TestFactory".equals(attribute.getName()))
    {
      return () -> {
        if (NamerFactory.isEmpty())
        {
          throw new FormattedException("When using dynamic tests and Approvals, you need to use %s instead.",
              "org.approvaltests.integrations.junit5.JUnit5Approvals.dynamicTest(String, Executable)");
        }
        return true;
      };
    }
    return () -> true;
  }
  public List<Method> getMethodsByName(Class<?> clazz, String methodName)
  {
    try
    {
      return Arrays.stream(clazz.getDeclaredMethods()).filter(m -> m.getName().equals(methodName))
          .collect(Collectors.toList());
    }
    catch (Throwable e)
    {
      return new ArrayList<>();
    }
  }
  @Override
  public void increment()
  {
    //ignore
  }
}
