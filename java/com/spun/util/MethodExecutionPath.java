package com.spun.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.lambda.query.Query;

import com.spun.util.filters.Filter;

public class MethodExecutionPath implements Serializable
{
  public static final Object      NULL_ENCOUNTERED_ON_PATH = new Object();
  private Class<? extends Object> classType, returnType;
  private String                  methodNames[];
  private Parameters[]            parameters;
  private Method                  methods[];
  /***********************************************************************/
  public MethodExecutionPath(Class<? extends Object> clazz, String methodName)
  {
    this(clazz, new String[]{methodName}, null);
  }
  /***********************************************************************/
  public MethodExecutionPath(Class<? extends Object> clazz, String... methodNames)
  {
    this(clazz, methodNames, null);
  }
  /***********************************************************************/
  public MethodExecutionPath(Class<? extends Object> clazz, String methodName, Parameters parameters)
  {
    this(clazz, new String[]{methodName}, new Parameters[]{parameters});
  }
  /***********************************************************************/
  public MethodExecutionPath(Class<? extends Object> clazz, String[] methodNames, Parameters[] parameters)
  {
    this.classType = clazz;
    this.methodNames = methodNames;
    this.parameters = parameters;
    this.methods = getRecursiveMethods(clazz, methodNames, parameters);
    this.returnType = (this.methods != null) ? methods[methods.length - 1].getReturnType() : null;
  }
  /************************************************************************/
  public static MethodExecutionPath method(Class<? extends Object> class1, String method, Object... paramaters)
  {
    return new MethodExecutionPath(class1, new String[]{method}, new Parameters[]{new Parameters(paramaters)});
  }
  public static Method[] getRecursiveMethods(Class<?> clazz, String[] methodNames, Parameters[] parameters)
  {
    if (clazz == null) { return null; }
    Method methods[] = new Method[methodNames.length];
    String currentMethodName = null;
    Parameters parameter = null;
    try
    {
      for (int i = 0; i < methods.length; i++)
      {
        currentMethodName = methodNames[i];
        parameter = Parameters.getParametersFor(parameters, i);
        methods[i] = parameter.getBestFitMethod(clazz, currentMethodName);
        clazz = methods[i].getReturnType();
      }
      return methods;
    }
    catch (Exception e)
    {
      throw new Error(
          "Unable to get method for " + clazz.getName() + "." + currentMethodName + "(" + parameter + ")", e);
    }
  }
  /***********************************************************************/
  public Object extractValue(Object object)
  {
    if (object == null) { return NULL_ENCOUNTERED_ON_PATH; }
    Method[] methods = this.methods == null
        ? getRecursiveMethods(object.getClass(), this.methodNames, this.parameters)
        : this.methods;
    for (int i = 0; i < methods.length; i++)
    {
      if (object == null) { return NULL_ENCOUNTERED_ON_PATH; }
      object = extractValue(object, methods[i], Parameters.getParametersFor(parameters, i).values);
    }
    return object;
  }
  /************************************************************************/
  private static Object extractValue(Object object, Method method, Object values[])
  {
    try
    {
      return method.invoke(object, values);
    }
    catch (Exception e)
    {
      throw new Error("Problems extracting values from " + object.getClass().getName() + "." + method.getName(),
          e);
    }
  }
  /************************************************************************/
  public Class<? extends Object> getClassType()
  {
    return classType;
  }
  /***********************************************************************/
  public Class<?> getReturnType()
  {
    return returnType;
  }
  /***********************************************************************/
  /***********************************************************************/
  public static class Parameters
  {
    public static final Parameters EMPTY = new Parameters(null, null);
    public Class<?>[]              definitions;
    public Object[]                values;
    /***********************************************************************/
    public Parameters(Object... values)
    {
      if (!ArrayUtils.isEmpty(values))
      {
        this.values = values;
        this.definitions = Query.select(values, m -> m.getClass()).toArray(new Class<?>[0]);
      }
    }
    /***********************************************************************/
    public Parameters(Class<?>[] definitions, Object[] values)
    {
      this.definitions = definitions;
      this.values = values;
    }
    /***********************************************************************/
    public Method getBestFitMethod(Class<?> clazz, String currentMethodName) throws NoSuchMethodException
    {
      return getBestFitMethod(clazz, currentMethodName, definitions);
    }
    /***********************************************************************/
    public static Method getBestFitMethod(Class<?> clazz, String currentMethodName, Class<?>[] definitions)
    {
      try
      {
        return clazz.getMethod(currentMethodName, definitions);
      }
      catch (NoSuchMethodException e)
      {
        List<Method> methods = Query.where(clazz.getMethods(),
            m -> new MethodParameterFilter(currentMethodName, definitions).isExtracted(m));
        if (methods.isEmpty()) { throw ObjectUtils.throwAsError(e); }
        if (methods.size() == 1)
        {
          return methods.get(0);
        }
        else
        {
          throw new Error("Don't know how to handle multiple available methods yet.");
        }
      }
    }
    /***********************************************************************/
    public static Parameters getParametersFor(Parameters[] parameters, int i)
    {
      if (parameters == null || (i >= parameters.length) || parameters[i] == null) { return EMPTY; }
      return parameters[i];
    }
    /***********************************************************************/
    public String toString()
    {
      return definitions == null ? "" : Arrays.asList(definitions).toString();
    }
  }
  public static class MethodParameterFilter implements Filter
  {
    private String     methodName;
    private Class<?>[] classParameters;
    /***********************************************************************/
    public MethodParameterFilter(String methodName, Class<?>[] classParameters)
    {
      super();
      this.methodName = methodName;
      this.classParameters = classParameters;
    }
    /***********************************************************************/
    public boolean isExtracted(Object object) throws IllegalArgumentException
    {
      ObjectUtils.assertInstance(Method.class, object);
      Method m = (Method) object;
      if (m.getName().equals(methodName) && m.getParameterTypes().length == classParameters.length)
      {
        Class<?>[] params = m.getParameterTypes();
        for (int i = 0; i < params.length; i++)
        {
          if (!ObjectUtils.isThisInstanceOfThat(classParameters[i], params[i])) { return false; }
        }
        return true;
      }
      else
      {
        return false;
      }
    }
  }
  /***********************************************************************/
  /***********************************************************************/
}
