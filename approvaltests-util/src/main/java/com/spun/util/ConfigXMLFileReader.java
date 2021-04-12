package com.spun.util;

import java.lang.reflect.Field;
import java.util.HashMap;

import com.spun.util.io.XMLUtils;

/**
 * @author Llewellyn Falco
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ConfigXMLFileReader
{
  public static String loadXML(Class<?> clazz, String exclude[], String fileLocation, String[] backupPaths)
      throws Exception
  {
    String fileNameUsed = XMLUtils.locateFile(fileLocation, backupPaths);
    loadXML(clazz, exclude, fileNameUsed);
    return fileNameUsed;
  }
  public static void loadXML(Class<?> clazz, String exclude[], String knownFileLocation) throws Exception
  {
    HashMap<String, Object> properties = XMLUtils.parseProperties(knownFileLocation, new ConfigXMLNodeExtractor());
    loadClass(clazz, exclude, properties);
  }
  private static void loadClass(Class<?> clazz, String exclude[], HashMap<String, Object> properties)
      throws IllegalArgumentException, IllegalAccessException
  {
    Field[] fields = ConfigXMLFileWriter.getFields(clazz, exclude);
    for (int i = 0; i < fields.length; i++)
    {
      assignField(fields[i], properties);
    }
    //My_System.variable("fields " ,ObjectUtils.extractArray(fields, "getName"));
  }
  private static void assignField(Field field, HashMap<String, Object> properties)
      throws IllegalArgumentException, IllegalAccessException
  {
    String name = field.getName();
    Object value = properties.get(name);
    if (value == null)
    {
    } // do nothing, leave default
    else if (field.getType().isAssignableFrom(String.class))
    {
      field.set(null, value);
    }
    else if (field.getType().isAssignableFrom(String[].class))
    {
      field.set(null, value);
    }
    else if (field.getType().isAssignableFrom(int.class))
    {
      field.setInt(null, NumberUtils.load((String) value, field.getInt(null)));
    }
    else if (field.getType().isAssignableFrom(double.class))
    {
      field.setDouble(null, NumberUtils.load((String) value, field.getDouble(null)));
    }
    else if (field.getType().isAssignableFrom(boolean.class))
    {
      field.setBoolean(null, NumberUtils.load((String) value, field.getBoolean(null)));
    }
    else
    {
      throw new Error("don't know how to handle field of type " + field.getType().getName());
    }
  }
}
