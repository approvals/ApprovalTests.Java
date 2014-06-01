package com.spun.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationUtils
{
  /**
   * Returns first occurrence of specified annotation if one is
   * found on method or class within current thread stack trace.
   * @param annotationClass to search for
   * @return found annotationClass or null if not found
   */
  public static <T extends Annotation> T getAnnotationFromStackTrace(Class<T> annotationClass)
  {
    StackTraceElement[] trace = Thread.currentThread().getStackTrace();
    for (StackTraceElement stack : trace)
    {
      Method method = null;
      Class<?> clazz = null;
      try
      {
        String methodName = stack.getMethodName();
        clazz = Class.forName(stack.getClassName());
        method = clazz.getMethod(methodName, (Class<?>[]) null);
      }
      catch (Exception e)
      {
        //ignore
      }
      T annotation = null;
      if (method != null)
      {
        annotation = method.getAnnotation(annotationClass);
      }
      if (annotation != null) { return annotation; }
      annotation = clazz!=null ? clazz.getAnnotation(annotationClass) : null;
      if (annotation != null) { return annotation; }
    }
    return null;
  }
}
