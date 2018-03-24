package com.spun.util;

import java.lang.reflect.Method;

import org.lambda.actions.Action0;

import com.spun.util.logger.SimpleLogger;

public class ThreadLauncher implements Runnable
{
  private Object   object       = null;
  private Object[] objectParams = null;
  private Method   method       = null;
  private long     delay;
  /***********************************************************************/
  public ThreadLauncher(Object object, Method method, Object[] objectParams, long delay)
  {
    this.delay = delay;
    this.object = object;
    this.method = method;
    this.objectParams = objectParams;
    new Thread(this).start();
  }
  /***********************************************************************/
  public ThreadLauncher(Object object, Method method, Object[] objectParams)
  {
    this(object, method, objectParams, 0);
  }
  /***********************************************************************/
  public ThreadLauncher(Class<?> clazz, String methodName, long delay) throws SecurityException, NoSuchMethodException
  {
    this(null, clazz.getMethod(methodName, (Class[]) null), null, delay);
  }
  /***********************************************************************/
  public ThreadLauncher(Class<?> clazz, String methodName) throws SecurityException, NoSuchMethodException
  {
    this(null, clazz.getMethod(methodName, (Class[]) null), null, 0);
  }
  /***********************************************************************/
  public ThreadLauncher(Object object, String methodName, long delay)
      throws SecurityException, NoSuchMethodException
  {
    this(object, object.getClass().getMethod(methodName, (Class[]) null), null, delay);
  }
  /***********************************************************************/
  public ThreadLauncher(Object object, String methodName) throws SecurityException, NoSuchMethodException
  {
    this(object, object.getClass().getMethod(methodName, (Class[]) null), null, 0);
  }
  /***********************************************************************/
  public ThreadLauncher(Object object, String methodName, Object[] objectParams, long delay)
      throws SecurityException, NoSuchMethodException
  {
    this(object, MethodExecutionPath.Parameters.getBestFitMethod(object.getClass(), methodName,
        getClassArray(objectParams)), objectParams, delay);
  }
  /***********************************************************************/
  private static Class[] getClassArray(Object[] objectParams)
  {
    Class[] classes = new Class[objectParams.length];
    for (int i = 0; i < objectParams.length; i++)
    {
      classes[i] = objectParams[i].getClass();
    }
    return classes;
  }
  /***********************************************************************/
  public void run()
  {
    try
    {
      Thread.sleep(delay);
      //My_System.event("Running " + method.getName());
      method.invoke(object, objectParams);
    }
    catch (Throwable t)
    {
      SimpleLogger.warning("Caught throwable exception ", t);
    }
  }
  /***********************************************************************/
  public static void launch(Action0 action)
  {
    new LambdaThreadLauncher(action);
  }
}
