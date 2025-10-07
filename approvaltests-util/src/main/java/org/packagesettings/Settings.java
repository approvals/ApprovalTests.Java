package org.packagesettings;

public class Settings
{
  private String location;
  public String getLocation()
  {
    return location;
  }

  public Object getValue()
  {
    return value;
  }
  private Object value;
  public Settings(Object value, String location)
  {
    this.value = value;
    this.location = location;
  }

  @Override
  public String toString()
  {
    return value + " [from " + location + "]";
  }
}
