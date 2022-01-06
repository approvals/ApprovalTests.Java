package org.packagesettings;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.lambda.functions.Function0;

import com.spun.util.ObjectUtils;
import com.spun.util.ThreadUtils;

public class PackageLevelSettings
{
  public static Map<String, Settings> get()
  {
    return getForStackTrace(ThreadUtils.getStackTrace());
  }
  public static Map<String, Settings> getForStackTrace(StackTraceElement[] trace)
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
      throw ObjectUtils.throwAsError(t);
    }
    return settings;
  }
  private static Map<String, Settings> getSettingsFor(String packageName, HashSet<String> done)
  {
    if (packageName == null || done.contains(packageName))
    { return Collections.emptyMap(); }
    Map<String, Settings> settings = new HashMap<String, Settings>();
    settings.putAll(getSettingsFor(getNextLevel(packageName), done));
    try
    {
      Class<?> clazz = loadClass(packageName + ".PackageSettings");
      Field[] declaredFields = clazz.getDeclaredFields();
      Object o = clazz.newInstance();
      for (Field field : declaredFields)
      {
        if (Modifier.isStatic(field.getModifiers()))
        {
          settings.put(field.getName(), getFieldValue(field, null));
        }
        else
        {
          settings.put(field.getName(), getFieldValue(field, o));
        }
      }
    }
    catch (Throwable e)
    {
      //Ignore
    }
    done.add(packageName);
    return settings;
  }
  private static Settings getFieldValue(Field field, Object from)
  {
    try
    {
      field.setAccessible(true);
      return new Settings(field.get(from), field.getDeclaringClass().getName());
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
  public static Class<?> loadClass(String className)
  {
    return ObjectUtils
        .throwAsError(() -> Class.forName(className, true, Thread.currentThread().getContextClassLoader()));
  }
  public static Object getValueFor(String key)
  {
    Settings settings = get().get(key);
    return settings == null ? null : settings.getValue();
  }
  public static <T> T getValueFor(org.packagesettings.Field<T> field)
  {
    return getValueFor(field, () -> null);
  }
  public static <T> T getValueFor(org.packagesettings.Field<T> field, Function0<T> defaultSupplier)
  {
    return field.getValue(get(), defaultSupplier);
  }
}
