package com.spun.util;

import java.lang.reflect.InvocationTargetException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.spun.util.introspection.ClassGetter;

public class UniversalTestSuite
{
  public static Test createFor(String path) throws InstantiationException, IllegalAccessException,
      ClassNotFoundException, IllegalArgumentException, SecurityException, InvocationTargetException,
      NoSuchMethodException
  {
    TestSuite suite = new TestSuite("Test for " + path);
    for (Class c : ClassGetter.getClasses(path, "Test"))
    {
      addClassToSuite(suite, c);
    }
    return suite;
  }
  public static void addClassToSuite(TestSuite suite, Class<?> clazz) throws IllegalArgumentException,
      SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
  {
    if (ObjectUtils.isThisInstanceOfThat(clazz, TestCase.class))
    {
      suite.addTest(new TestSuite(clazz));
    }
    else if (ObjectUtils.isThisInstanceOfThat(clazz, TestSuite.class))
    {
      suite.addTest((TestSuite) clazz.getMethod("suite").invoke(null));
    }
  }

}
