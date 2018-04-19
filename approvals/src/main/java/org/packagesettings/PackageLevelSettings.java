package org.packagesettings;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.spun.util.ThreadUtils;

public class PackageLevelSettings
{
  public static Map<String, Settings> get()
  {
    return getForStackTrace(ThreadUtils.getStackTrace());
  }
  public static Map<String, Settings> getForStackTrace(StackTraceElement[] trace) throws Error
  {
    Map<String, Settings> settings = new HashMap<String, Settings>();
    try
    {
      HashSet<String> done = new HashSet<String>();
      for (StackTraceElement element : trace)
      {
        String packageName = getNextLevel(element.getClassName());
        settings.putAll(getSettingsFor(packageName, done));
      }
    }
    catch (Throwable t)
    {
      throw throwAsError(t);
    }
    return settings;
  }
  private static Map<String, Settings> getSettingsFor(String packageName, HashSet<String> done)
  {
    if (packageName == null || done.contains(packageName)) { return Collections.emptyMap(); }
    Map<String, Settings> settings = new HashMap<String, Settings>();
    settings.putAll(getSettingsFor(getNextLevel(packageName), done));
    try
    {
      Class<?> clazz = loadClass(packageName + ".PackageSettings");
      Field[] declaredFields = clazz.getDeclaredFields();
      for (Field field : declaredFields)
      {
        if (Modifier.isStatic(field.getModifiers()))
        {
          settings.put(field.getName(), getFieldValue(field));
        }
      }
    }
    catch (ClassNotFoundException e)
    {
      //Ignore
    }
    done.add(packageName);
    return settings;
  }
  private static Settings getFieldValue(Field field)
  {
    try
    {
      return new Settings(field.get(null), field.getDeclaringClass().getName());
    }
    catch (Throwable t)
    {
      //ignore
    }
    return null;
  }
  public static String getNextLevel(String className)
  {
    int last = className.lastIndexOf(".");
    return (last < 0) ? null : className.substring(0, last);
  }
  public static Class<?> loadClass(String className) throws ClassNotFoundException
  {
    return Class.forName(className, true, Thread.currentThread().getContextClassLoader());
  }
  public static Error throwAsError(Throwable t) throws Error
  {
    if (t instanceof RuntimeException)
    {
      throw (RuntimeException) t;
    }
    else if (t instanceof Error)
    {
      throw (Error) t;
    }
    else
    {
      throw new Error(t);
    }
  }
}
