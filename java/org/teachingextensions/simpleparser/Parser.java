package org.teachingextensions.simpleparser;

import java.lang.reflect.Field;
import java.util.List;

import org.lambda.functions.Function1;
import org.lambda.query.Query;

import com.spun.util.ObjectUtils;
import com.spun.util.io.FileUtils;

public class Parser
{
  public static String parse(String text, Object data)
  {
    return parse(text, "{", "}", data);
  }
  public static String parse(String text, String startCharacter, String endCharacter, Object data)
  {
    StringBuffer sb = new StringBuffer();
    String temp = text;
    int start = temp.indexOf(startCharacter);
    while (start > 0)
    {
      int end = temp.indexOf(endCharacter, start);
      sb.append(temp.substring(0, start));
      String key = temp.substring(start + startCharacter.length(), end);
      sb.append(getValue(key, data));
      temp = temp.substring(end + endCharacter.length());
      start = temp.indexOf(startCharacter);
    }
    sb.append(temp);
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
  public static String parseRtfFile(String fileName, Object data)
  {
    try
    {
      String text = FileUtils.readFromClassPath(data.getClass(), fileName);
      return parse(text, "\\{", "\\}", data);
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
}
