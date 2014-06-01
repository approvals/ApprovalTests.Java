package com.spun.util;

import java.util.ArrayList;

public class CallMethod
{
  private final Class<? extends Object>             clazz;
  private ArrayList<String>                         methodNames = new ArrayList<String>();
  private ArrayList<MethodExecutionPath.Parameters> params      = new ArrayList<MethodExecutionPath.Parameters>();
  public CallMethod(Class<? extends Object> clazz)
  {
    this.clazz = clazz;
  }
  public CallMethod method(String methodName, Object... parameters)
  {
    methodNames.add(methodName);
    if (!ArrayUtils.isEmpty(parameters))
    {
      params.add(new MethodExecutionPath.Parameters(parameters));
    }
    else {
      params.add(new MethodExecutionPath.Parameters((Object[])null));
    }
    return this;
  }
  public MethodExecutionPath go()
  {
    return new MethodExecutionPath(clazz, methodNames.toArray(new String[0]), params.toArray(new MethodExecutionPath.Parameters[0]));
  }
}
