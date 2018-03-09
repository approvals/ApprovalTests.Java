package com.spun.util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lambda.functions.Function1;

import com.spun.util.io.FileUtils;

public class ClassUtils
{
  /************************************************************************/
  public static String getClassName(Class<?> clazz)
  {
    String name = clazz.getName();
    int divider = name.lastIndexOf(".");
    return name.substring(divider + 1);
  }
  /************************************************************************/
  public static String getClassPath(Class<?> clazz)
  {
    String name = clazz.getName();
    int divider = name.lastIndexOf(".");
    return name.substring(0, divider);
  }
  /************************************************************************/
  public static Class<?> getWrapperClass(Class<?> primativeType)
  {
    if (boolean.class.equals(primativeType))
    {
      return Boolean.class;
    }
    else if (float.class.equals(primativeType))
    {
      return Float.class;
    }
    else if (long.class.equals(primativeType))
    {
      return Long.class;
    }
    else if (int.class.equals(primativeType))
    {
      return Integer.class;
    }
    else if (short.class.equals(primativeType))
    {
      return Short.class;
    }
    else if (byte.class.equals(primativeType))
    {
      return Byte.class;
    }
    else if (double.class.equals(primativeType))
    {
      return Double.class;
    }
    else if (char.class.equals(primativeType))
    {
      return Character.class;
    }
    else
    {
      return primativeType;
    }
  }
  /************************************************************************/
  /************************************************************************/
  public static boolean hasMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes)
  {
    try
    {
      return clazz.getMethod(methodName, parameterTypes) != null;
    }
    catch (SecurityException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
    catch (NoSuchMethodException e)
    {
      return false;
    }
  }
  public static File getSourceDirectory(Class<?> clazz)
  {
    return getSourceDirectory(clazz, new Function1<String, String>()
    {
      public String call(String fileName)
      {
        return fileName + ".java";
      }
    });
  }
  public static File getSourceDirectory(Class<?> clazz, Function1<String, String> createLastFileName)
  {
    final String name = clazz.getName();
    String[] split = name.split("\\.");
    split[split.length - 1] = createLastFileName.call(split[split.length - 1]);
    File found = find(new File("."), Arrays.asList(split));
    if (found == null) { throw new FormattedException("Didn't find %s under %s", name,
        FileUtils.getCurrentDirectory()); }
    return found.getParentFile();
  }
  public static File getSourceDirectory(Class<?> clazz, final String fileName)
  {
    return getSourceDirectory(clazz, new Function1<String, String>()
    {
      public String call(String __)
      {
        return fileName;
      }
    });
  }
  public static File find(File file, List<String> matches)
  {
    ArrayList<String> copy = new ArrayList<String>();
    copy.addAll(matches);
    copy.add(0, "*");
    return find2(file, copy);
  }
  public static File find2(File file, List<String> matches)
  {
    SearchingFileFilter searchingFileFilter = new SearchingFileFilter(matches);
    File[] listFiles = file.listFiles(searchingFileFilter);
    for (File file2 : listFiles)
    {
      if (file2.isDirectory())
      {
        File found = find2(file2, searchingFileFilter.getSubset(file2.getName()));
        if (found != null) { return found; }
      }
      else
      {
        return file2;
      }
    }
    return null;
  }
  public static <T> T create(Class<T> clazz)
  {
    try
    {
      return clazz.newInstance();
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  public static boolean IsPublicStatic(Field field)
  {
    return field != null && Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers());
  }
  public static boolean isPrimitiveField(Field field)
  {
    return field != null && (field.getType().isArray() || field.getType().isPrimitive()
        || field.getType().isAssignableFrom(String.class));
  }
}
