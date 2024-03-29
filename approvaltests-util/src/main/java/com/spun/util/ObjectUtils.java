package com.spun.util;

import org.lambda.actions.Action0;
import org.lambda.actions.Action0WithException;
import org.lambda.functions.Function0WithException;
import org.lambda.query.Query;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * A static class of convenience functions for Manipulating objects
 **/
public class ObjectUtils
{
  public static Class<?> loadClass(String className)
  {
    return throwAsError(() -> Class.forName(className, true, Thread.currentThread().getContextClassLoader()));
  }
  public static int generateHashCode(Object... relevantMembers)
  {
    final int PRIME = 31;
    int result = 1;
    for (Object member : relevantMembers)
    {
      result = PRIME * result + ((member == null) ? 0 : member.hashCode());
    }
    return result;
  }
  /**
   * tests if two objects are equal for all functions passed.
   **/
  public static boolean isEqualForMethods(Object o1, Object o2, String... methods)
  {
    try
    {
      Method[] m1 = getMethodsForObject(o1, methods);
      Method[] m2 = getMethodsForObject(o2, methods);
      for (int i = 0; i < m1.length; i++)
      {
        Object v1 = m1[i].invoke(o1, (Object[]) null);
        Object v2 = m2[i].invoke(o2, (Object[]) null);
        if (!isEqual(v1, v2))
        { return false; }
      }
      return true;
    }
    catch (Throwable t)
    {
      throw new Error(t);
    }
  }
  public static Method[] getMethodsForObject(Object o2, String[] passedMethods)
  {
    Method[] methods = new Method[passedMethods.length];
    Class<?> clazz = o2.getClass();
    for (int i = 0; i < passedMethods.length; i++)
    {
      String methodName = passedMethods[i];
      Class[] parameters = null;
      methods[i] = throwAsError(() -> clazz.getMethod(methodName, parameters));
    }
    return methods;
  }
  /**
   * A convenience function to check if 2 strings are equal.
   **/
  public static boolean isEqual(Object s1, Object s2)
  {
    if (s1 == s2)
    {
      return true;
    }
    else if ((s1 != null) && s1.equals(s2))
    {
      return true;
    }
    else
    {
      return false;
    }
  }
  public static boolean isIn(Object target, Object[] objects)
  {
    for (int i = 0; i < objects.length; i++)
    {
      if (ObjectUtils.isEqual(objects[i], target))
      { return true; }
    }
    return false;
  }
  public static boolean isThisInstanceOfThat(Class<?> thiz, Class<?> that)
  {
    return that.isAssignableFrom(thiz);
  }
  public static Error throwAsError(Throwable t)
  {
    if (t instanceof RuntimeException)
    {
      throw (RuntimeException) t;
    }
    else if (t instanceof Error)
    {
      throw (Error) t;
    }
    else
    {
      throw new Error(t);
    }
  }
  public static <T> T getRandomIndex(T[] array)
  {
    if ((array == null) || (array.length == 0))
    { return null; }
    return array[NumberUtils.RANDOM.nextInt(array.length)];
  }
  public static Method getGreatestCommonDenominator(Object[] from, String methodName)
  {
    List<Class<?>> classes = new ArrayList<>();
    ArrayUtils.addArray(classes, getAllCastableClasses(from[0]));
    for (Object o : from)
    {
      for (int i = classes.size() - 1; i >= 0; i--)
      {
        Class<?> clazz = classes.get(i);
        if (!isThisInstanceOfThat(o.getClass(), clazz) || !ClassUtils.hasMethod(clazz, methodName))
        {
          classes.remove(i);
        }
      }
    }
    return classes.size() == 0
        ? null
        : throwAsError(() -> ArrayUtils.getLast(classes).getMethod(methodName, (Class[]) null));
  }
  private static Class<?>[] getAllCastableClasses(Object object)
  {
    Class<? extends Object> clazz = object.getClass();
    ArrayList<Object> list = new ArrayList<Object>();
    while (clazz != null)
    {
      list.add(clazz);
      ArrayUtils.addArray(list, clazz.getInterfaces());
      clazz = clazz.getSuperclass();
    }
    Class<?>[] found = list.toArray(new Class<?>[list.size()]);
    ArrayUtils.toReverseArray(found);
    return found;
  }
  public static Object executeMethod(Object object, String method, Class<?>[] methodSignature, Object[] parameters)
  {
    try
    {
      return object.getClass().getMethod(method, methodSignature).invoke(object, parameters);
    }
    catch (Throwable t)
    {
      throw throwAsError(t);
    }
  }
  public static void assertInstance(Class<?> clazz, Object object)
  {
    assertInstance(new Class[]{clazz}, object);
  }
  public static void assertInstance(Class<?>[] classes, Object object)
  {
    if (object == null)
    {
      throw new NullPointerException(
          "Expected Object of Type " + Query.select(classes, Class::getName) + " but was null");
    }
    for (int i = 0; i < classes.length; i++)
    {
      if (ClassUtils.getWrapperClass(classes[i]).isInstance(object))
      { return; }
    }
    throw new IllegalArgumentException("Expected Object of Type " + Query.select(classes, Class::getName)
        + " but got " + object.getClass().getName());
  }
  public static String getClassName(Object o)
  {
    return o == null ? "null" : o.getClass().getName();
  }
  public static void assertInstanceOrNull(Class<?> type, Object value)
  {
    if (value != null)
    {
      assertInstance(type, value);
    }
  }
  public static void move(Object from, Object to, String[] getters)
  {
    try
    {
      for (String method : getters)
      {
        Method getMethod = from.getClass().getMethod("get" + method, (Class[]) null);
        Object value = getMethod.invoke(from, (Object[]) null);
        Method m = MethodExecutionPath.Parameters.getBestFitMethod(to.getClass(), "set" + method,
            new Class[]{getBestClass(value, getMethod)});
        m.invoke(to, value);
      }
    }
    catch (Exception e)
    {
      throw throwAsError(e);
    }
  }
  private static Class<?> getBestClass(Object value, Method method)
  {
    return value == null ? method.getReturnType() : value.getClass();
  }
  public static boolean isClassPresent(String className)
  {
    try
    {
      Class<?> loadClass = loadClass(className);
      return loadClass != null;
    }
    catch (Throwable e)
    {
      return false;
    }
  }
  public static Throwable captureException(Action0 runnableBlock)
  {
    try
    {
      runnableBlock.call();
    }
    catch (Throwable t)
    {
      return t;
    }
    return null;
  }
  public static <T> T throwAsError(Function0WithException<T> code)
  {
    try
    {
      return code.call();
    }
    catch (Throwable t)
    {
      throw ObjectUtils.throwAsError(t);
    }
  }
  public static void throwAsError(Action0WithException code)
  {
    try
    {
      code.call();
    }
    catch (Throwable t)
    {
      throw ObjectUtils.throwAsError(t);
    }
  }
}
