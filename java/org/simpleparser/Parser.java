package org.simpleparser;

import java.lang.reflect.Field;
import java.util.List;

import org.lambda.functions.Function1;
import org.lambda.query.Query;

public class Parser
{
  public static String Parse(String text, Object data)
  {
    StringBuffer sb = new StringBuffer();
    boolean opened = false;
    String piece = "";
    for (char t : text.toCharArray())
    {
      if (opened)
      {
        if (t != '}')
        {
          piece += t;
        }
        else
        {
          opened = false;
          sb.append(getValue(piece, data));
        }
      }
      else
      {
        if (t == '{')
        {
          opened = true;
          piece = "";
        }
        else
        {
          sb.append(t);
        }
      }
    }
    return sb.toString();
  }
  private static String getValue(final String piece, Object data)
  {
    try
    {
      Field[] declaredFields = data.getClass().getDeclaredFields();
      List<Field> f = Query.where(declaredFields, new Function1<Field, Boolean>()
      {
        @Override
        public Boolean call(Field i)
        {
          return i.getName().equals(piece);
        }
      });
      f.get(0).setAccessible(true);
      return "" + f.get(0).get(data);
    }
    catch (Exception e)
    {
      return piece;
    }
  }
}
