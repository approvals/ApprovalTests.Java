package com.spun.util;

import java.lang.reflect.Method;

import org.lambda.actions.Action0;

/**
 * @deprecated use {@literal LambdaThreaLauncher( ()-> object.method(params))  }
 */
@Deprecated
public class ThreadLauncher
{
  
  public ThreadLauncher(Object object, Method method, Object[] objectParams, long delay)
  {
    String params = StringUtils.join(objectParams, ",", o -> "" + o);
    throw new DeprecatedException("new LambdaThreadLauncher(()-> object.%s(%s))", method.getName(), params);
  }
  
  public ThreadLauncher(Object object, Method method, Object[] objectParams)
  {
    this(object, method, objectParams, 0);
  }
  
  public ThreadLauncher(Class<?> clazz, String methodName, long delay)
  {
    this(null, ClassUtils.getMethod(clazz, methodName), null, delay);
  }
  
  public ThreadLauncher(Class<?> clazz, String methodName)
  {
    this(null, ClassUtils.getMethod(clazz, methodName), null, 0);
  }
  
  public ThreadLauncher(Object object, String methodName, long delay)
  {
    this(object, ClassUtils.getMethod(object.getClass(), methodName), null, delay);
  }
  
  public ThreadLauncher(Object object, String methodName)
  {
    this(object, ClassUtils.getMethod(object.getClass(), methodName), null, 0);
  }
  
  public static void launch(Action0 action)
  {
    throw new DeprecatedException("new LambdaThreadLauncher(action)");
  }
}
