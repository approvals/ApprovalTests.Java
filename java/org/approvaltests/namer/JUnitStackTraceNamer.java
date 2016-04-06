package org.approvaltests.namer;

import java.io.File;
import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.junit.Test;

import com.spun.util.ObjectUtils;
import com.spun.util.io.StackElementSelector;
import com.spun.util.tests.StackTraceReflectionResult;
import com.spun.util.tests.TestUtils;

public class JUnitStackTraceNamer implements ApprovalNamer
{
  private StackTraceReflectionResult info;
  public JUnitStackTraceNamer()
  {
    info = TestUtils.getCurrentFileForMethod(new JUnit3StackSelector());
  }
  @Override
  public String getApprovalName()
  {
    return String.format("%s.%s", info.getClassName(), info.getMethodName());
  }
  @Override
  public String getSourceFilePath()
  {
    return info.getSourceFile().getAbsolutePath() + File.separator;
  }
  /**************************************************************/
  /*                    INNER CLASSES                           */
  /**************************************************************/
  public static class JUnit3StackSelector implements StackElementSelector
  {
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
      throw new RuntimeException("Could not find Junit TestCase you are running");
    }
    private boolean isTestCase(StackTraceElement element) throws ClassNotFoundException
    {

      String fullClassName = element.getClassName();
      Class<?> clazz = null;
      try {
        clazz = ObjectUtils.loadClass( fullClassName );
      } catch (ClassNotFoundException e) {
        return false;
      }
      boolean junit3 = ObjectUtils.isThisInstanceOfThat(clazz, TestCase.class);
      if (!junit3)
      {
        junit3 = isTestAttribute(clazz, element.getMethodName());
      }
      return junit3;
    }
    private boolean isTestAttribute(Class<?> clazz, String methodName) throws ClassNotFoundException, SecurityException
    {
      Method method;
      try
      {
        method = clazz.getMethod(methodName);
      }
      catch (Throwable e)
      {
        return false;
      } 
      
      return method.isAnnotationPresent(Test.class);
    }
  }
}
