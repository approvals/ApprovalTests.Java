package org.lambda.functions.implementations;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import com.spun.util.ObjectUtils;

public class Function<In1, In2, In3, In4, In5, In6, In7, In8, In9, Out>
{
  public Out                    returnValue;
  private final Object[]        extraVariables;
  private Object[]              params;
  private int                   startAt = 0;
  private int                   totalLengthOfParameters;
  private Constructor<Function> constructor;
  public In1                    a;
  public In2                    b;
  public In3                    c;
  public In4                    d;
  public In5                    e;
  public In6                    f;
  public In7                    g;
  public In8                    h;
  public In9                    i;
  public Function(Object[] extraVariables)
  {
    this.extraVariables = extraVariables;
  }
  protected Out call(Object[] mainParams)
  {
    Object[] parameters = null;
    try
    {
      initalizeConstructor();
      parameters = getParameters(totalLengthOfParameters, extraVariables, mainParams);
      Function intsance = constructor.newInstance(parameters);
      return (Out) intsance.returnValue;
    }
    catch (IllegalArgumentException e)
    {
      throw new RuntimeException(e.getMessage() + "\nExpected " + Arrays.toString(constructor.getParameterTypes())
          + "\nGot " + Arrays.toString(parameters));
    }
    catch (Throwable e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  @SuppressWarnings({"unchecked", "rawtypes"})
  public void initalizeConstructor()
  {
    if (constructor == null)
    {
      Class<? extends Function> clazz = this.getClass();
      constructor = (Constructor<Function>) clazz.getDeclaredConstructors()[0];
      constructor.setAccessible(true);
      totalLengthOfParameters = constructor.getParameterTypes().length;
    }
  }
  private Object[] getParameters(int length, Object[] extraVariables2, Object[] mainParams)
  {
    if (params == null)
    {
      params = initalizeParameters(length, extraVariables2, mainParams);
    }
    for (int i = 0; i < mainParams.length; i++)
    {
      params[i + startAt] = mainParams[i];
    }
    return params;
  }
  private Object[] initalizeParameters(int total, Object[] extraVariables, Object[] mainParams)
  {
    ArrayList<Object> list = new ArrayList<Object>(total);
    if ((extraVariables.length + 1 + mainParams.length) != total)
    {
      startAt = 1;
      list.add(getParentThisReference());
    }
    for (Object object : mainParams)
    {
      list.add(object);
    }
    list.add(extraVariables);
    for (Object object : extraVariables)
    {
      list.add(object);
    }
    return list.toArray();
  }
  private Object getParentThisReference()
  {
    Object parent = tryToGetParentByName();
    if (parent == null)
    {
      parent = tryToGetParentByType();
    }
    return parent;
  }
  private Object tryToGetParentByType()
  {
    try
    {
      Class<? extends Function> clazz = this.getClass();
      Field[] fields = clazz.getDeclaredFields();
      Class<?> desiredType = clazz.getDeclaredConstructors()[0].getParameterTypes()[0];
      for (Field field : fields)
      {
        if (field.getType().equals(desiredType))
        {
          field.setAccessible(true);
          return field.get(this);
        }
      }
    }
    catch (Exception e)
    {
      // Couldn't find method of parent type.
    }
    return null;
  }
  private Object tryToGetParentByName()
  {
    try
    {
      Class<? extends Function> clazz = this.getClass();
      Field parentField = clazz.getDeclaredField("this$0");
      parentField.setAccessible(true);
      return parentField.get(this);
    }
    catch (Exception e)
    {
      // Failed try 1 -> this$0 field doesn't exist
    }
    return null;
  }
  public void returnValue(Out returnValue)
  {
    this.returnValue = returnValue;
  }
  public void ret(Out returnValue)
  {
    returnValue(returnValue);
  }
}
