package org.approvaltests.namer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.spun.util.ObjectUtils;
import com.spun.util.io.StackElementSelector;
import com.spun.util.tests.TestUtils;

public class AttributeStackSelector implements StackElementSelector
{
  public static String                      classNames[] = {"org.testng.annotations.Test",
                                                            "org.junit.Test",
                                                            "org.junit.jupiter.api.Test",
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
      else if (inTestCase) { return trace[i - 1]; }
    }
    throw new RuntimeException("Could not find Junit/TestNg TestCase you are running, supported frameworks: Junit3, Junit4, Junit5, TestNg");
  }
  private boolean isTestCase(StackTraceElement element) throws ClassNotFoundException
  {
    String fullClassName = element.getClassName();
    Class<?> clazz = loadClass(fullClassName);
    if (clazz == null) { return false; }
    if (isJunit3Test(clazz)) { return true; }
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
    Method method = getMethodByName(clazz, methodName);
    if (method == null) { return false; }
    for (Class<? extends Annotation> attribute : attributes)
    {
      if (method.isAnnotationPresent(attribute)) { return true; }
    }
    return false;
  }
  public Method getMethodByName(Class<?> clazz, String methodName)
  {
    Method method = null;
    try
    {
      Method[] declaredMethods = clazz.getDeclaredMethods();
      for (Method m : declaredMethods)
      {
        if (m.getName().equals(methodName))
        {
          method = m;
        }
      }
    }
    catch (Throwable e)
    {
    }
    return method;
  }
  @Override
  public void increment()
  {
    //ignore
  }
}
