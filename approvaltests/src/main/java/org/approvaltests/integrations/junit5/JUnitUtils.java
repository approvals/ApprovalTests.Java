package org.approvaltests.integrations.junit5;

import com.spun.util.tests.TestUtils;
import org.approvaltests.namer.AttributeStackSelector;
import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.commons.util.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.List;

public class JUnitUtils
{
  public static boolean isTestableMethodForJunit(StackTraceElement element)
  {
    String fullClassName = element.getClassName();
    Class<?> clazz = AttributeStackSelector.loadClass(fullClassName);
    String methodName = TestUtils.unrollLambda(element.getMethodName());
    List<Method> methods = AttributeStackSelector.getMethodsByName(clazz, methodName);
    if (methods.isEmpty())
    { return false; }
    for (Method method : methods)
    {
      // TODO: TestFactory might needs special attention
      if (AnnotationUtils.isAnnotated(method, Testable.class))
      { return true; }
    }
    return false;
  }
}
