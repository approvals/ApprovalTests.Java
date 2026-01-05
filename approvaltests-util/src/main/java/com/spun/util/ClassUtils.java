package com.spun.util;

import com.spun.util.io.FileUtils;
import org.lambda.functions.Function1;
import org.lambda.query.OrderBy.Order;
import org.lambda.query.Query;
import org.lambda.query.Queryable;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ClassUtils
{
  public static final String APPROVALTESTS_PROJECT_DIRECTORY = "APPROVALTESTS_PROJECT_DIRECTORY";
  public static String getClassName(Class<?> clazz)
  {
    String name = clazz.getName();
    int divider = name.lastIndexOf(".");
    return name.substring(divider + 1);
  }

  public static String getClassPath(Class<?> clazz)
  {
    String name = clazz.getName();
    int divider = name.lastIndexOf(".");
    return name.substring(0, divider);
  }

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
    String baseSourcePath = getProjectRootPath();
    File found = find(new File(baseSourcePath), Arrays.asList(split));
    if (found == null)
    { throw new FormattedException("Didn't find %s under %s", name, FileUtils.getCurrentDirectory()); }
    return found.getParentFile();
  }

  /**
   * This returns the {@link com.spun.util.ClassUtils#APPROVALTESTS_PROJECT_DIRECTORY APPROVALTESTS_PROJECT_DIRECTORY} system property if it is set, otherwise it returns the working directory.
   */
  public static String getProjectRootPath()
  {
    if (System.getProperties().containsKey(APPROVALTESTS_PROJECT_DIRECTORY))
    { return System.getProperty(APPROVALTESTS_PROJECT_DIRECTORY); }
    if (System.getenv().containsKey(APPROVALTESTS_PROJECT_DIRECTORY))
    { return System.getenv(APPROVALTESTS_PROJECT_DIRECTORY); }
    return ".";
  }

  public static File getSourceDirectory(Class<?> clazz, final String fileName)
  {
    return getSourceDirectory(clazz, __ -> fileName);
  }

  public static File find(File file, List<String> matches)
  {
    // Try with full package path first, then progressively strip leading segments.
    // This supports Kotlin's recommended directory structure where the common root
    // package is omitted from the directory structure.
    // See: https://github.com/approvals/ApprovalTests.Java/issues/352
    for (int skipSegments = 0; skipSegments < matches.size(); skipSegments++)
    {
      List<String> reducedMatches = matches.subList(skipSegments, matches.size());
      ArrayList<String> copy = new ArrayList<String>();
      copy.addAll(reducedMatches);
      copy.add(0, "*");
      File found = find2(file, copy);
      if (found != null)
      { return found; }
    }
    return null;
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
        if (found != null)
        { return found; }
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

  public static Method getMethod(Class<?> clazz, String methodName)
  {
    try
    {
      return clazz.getMethod(methodName, (Class[]) null);
    }
    catch (NoSuchMethodException | SecurityException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }

  public static <T> Class<?> getGreatestCommonBaseType(List<T> list)
  {
    if (list == null)
    { return Object.class; }
    list = Query.where(list, Objects::nonNull);
    if (list.isEmpty())
    { return Object.class; }
    Class greatestCommonType = list.get(0).getClass();
    for (T t : list)
    {
      if (t != null && !greatestCommonType.isInstance(t))
      {
        greatestCommonType = greatestCommonType.getSuperclass();
      }
    }
    if (Object.class.equals(greatestCommonType))
    {
      greatestCommonType = getGreatestCommonInterface(list);
    }
    return greatestCommonType;
  }

  /**
   * Greatest Common Interface is the shared interface with the most methods. In case of a tie,
   * it's the first declared in the first item of the list.
   */
  public static <T> Class getGreatestCommonInterface(List<T> list)
  {
    List<Class<?>> allInterfaces = org.apache.commons.lang3.ClassUtils.getAllInterfaces(list.get(0).getClass());
    for (int i = 1; i < list.size(); i++)
    {
      for (int j = allInterfaces.size() - 1; 0 < j; j--)
      {
        if (!allInterfaces.get(j).isInstance(list.get(i)))
        {
          allInterfaces.remove(j);
        }
      }
    }
    List<Class<?>> allCommon = allInterfaces;
    Class first = Queryable.as(allCommon).orderBy(Order.Descending, x -> x.getMethods().length).first();
    return first;
  }

  public static File getAdjacentFile(Class<?> aClass, String relativeFileName)
  {
    return new File(getSourceDirectory(aClass), relativeFileName);
  }
}
