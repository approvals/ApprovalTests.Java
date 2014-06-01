package org.approvaltests.hadoop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WritableUtils
{

  public static <T> T createWritable(Object value, Class<T> writableType) throws InstantiationException,
      IllegalAccessException, InvocationTargetException, NoSuchMethodException
  {
    if (writableType.isInstance(value)) { return (T) value; }
    Object writable = writableType.newInstance();
    try
    {
      Method method = writableType.getMethod("set", value.getClass());
      method.invoke(writable, value);
      return (T) writable;
    }
    catch (Exception e)
    {
      // TODO: handle exception
    }
    for (Method m : writableType.getMethods())
    {
      if (m.getName().equals("set") && m.getParameterTypes().length == 1) // needed for string
      {
        try
        {
          m.invoke(writable, value);
        }
        catch (Exception e)
        {
          throw new RuntimeException(String.format("Could not convert %s => %s", value.getClass().getName(),
              writableType.getName()), e);
        }
      }
    }
    return (T) writable;
  }
}
