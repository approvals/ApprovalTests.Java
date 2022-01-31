package org.packagesettings;

import org.lambda.functions.Function0;

import java.util.Map;

public class Field<T>
{
  public final String   Name;
  public final Class<T> Class;
  public Field(String name, Class<T> clazz)
  {
    this.Name = name;
    this.Class = clazz;
  }
  public boolean isPresent(Map<String, Settings> settings)
  {
    Settings value = settings.get(Name);
    return value != null && this.Class.isInstance(value.getValue());
  }
  public T getValue(Map<String, Settings> settings, Function0<T> defaultValue)
  {
    return isPresent(settings) ? (T) settings.get(Name).getValue() : defaultValue.call();
  }
  public String getFieldName()
  {
    return Name;
  }
  public Class<T> getFieldClass()
  {
    return Class;
  }
}
