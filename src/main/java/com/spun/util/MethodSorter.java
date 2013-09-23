package com.spun.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

public class MethodSorter<T> implements java.util.Comparator<T>, Serializable
{
  public static enum SortOrder {
    Ascending, Decending;
    public boolean toBoolean()
    {
      return this == Ascending;
    }
    public static SortOrder isAscending(boolean ascending)
    {
      return ascending ? Ascending : Decending;
    }
  }
  private Comparator<Object>  comparator;
  private MethodExecutionPath path;
  /************************************************************************/
  public MethodSorter(MethodExecutionPath path, SortOrder sortOrder)
  {
    SortOrder s = sortOrder;
    this.path = path;
    this.comparator = getComparatorForType(path.getReturnType(), s.toBoolean());
  }
  /************************************************************************/
  public MethodSorter(Class<? extends T> classType, String methodName, boolean ascending)
  {
    this(new MethodExecutionPath(classType, methodName), SortOrder.isAscending(ascending));
  }
  /************************************************************************/
  public MethodSorter(Class<? extends T> classType, String[] methodNames, boolean ascending)
  {
    
    this(new MethodExecutionPath(classType, methodNames), SortOrder.isAscending(ascending));
  }
  /************************************************************************/
  public Class getClassType()
  {
    return path.getClassType();
  }
  /************************************************************************/
  private static Comparator<Object> getComparatorForType(Class type, boolean ascending)
  {
    if (String.class.isAssignableFrom(type))
    {
      return new StringComparator(ascending);
    }
    else if (Double.TYPE.isAssignableFrom(type))
    {
      return new NumberComparator(ascending);
    }
    else if (Integer.TYPE.isAssignableFrom(type) || Integer.class.isAssignableFrom(type))
    {
      return new NumberComparator(ascending);
    }
    else if (Date.class.isAssignableFrom(type))
    {
      return new DateComparator(ascending);
    }
    else if (Character.TYPE.isAssignableFrom(type))
    {
      return new CharacterComparator(ascending);
    }
    else if (type.isArray())
    {
      return new ArraySizeComparator(ascending);
    }
    else
    {
      throw new Error("Cannot handle class of type " + type.getName());
    }
  }
  /************************************************************************/
  private Object extractValue(T object)
  {
    Object o = path.extractValue(object);
    return (MethodExecutionPath.NULL_ENCOUNTERED_ON_PATH == o) ? null : o;
  }
  /************************************************************************/
  public int compare(T o1, T o2)
  {
    return comparator.compare(extractValue(o1), extractValue(o2));
  }
  private static int compareNull(Object o1, Object o2)
  {
    if (o1 == o2) { return 0; }
    return (o1 == null) ? -1 : 1;
  }
  /************************************************************************/
  /************************************************************************/
  /*                  INNNER CLASSES                                      */
  /************************************************************************/
  public static class NumberComparator implements Comparator<Object>, Serializable
  {
    private boolean ascending;
    /************************************************************************/
    public NumberComparator(boolean ascending)
    {
      this.ascending = ascending;
    }
    /************************************************************************/
    public int compare(Object o1, Object o2) throws java.lang.ClassCastException
    {
      int value = 0;
      if (o1 == null || o2 == null)
      {
        value = compareNull(o1, o2);
      }
      else
      {
        value = Double.compare(((Number) o1).doubleValue(), ((Number) o2).doubleValue());
      }
      return ascending ? value : -value;
    }
  }
  /***********************************************************************/
  public static class DateComparator implements Comparator<Object>, Serializable
  {
    private boolean ascending;
    /************************************************************************/
    public DateComparator(boolean ascending)
    {
      this.ascending = ascending;
    }
    /************************************************************************/
    public int compare(Object o1, Object o2)
    {
      if (o1 == null || o2 == null) { return compareNull(o1, o2); }
      Date date1 = (Date) o1;
      Date date2 = (Date) o2;
      return date1.compareTo(date2) * (ascending ? 1 : -1);
    }
  }
  /***********************************************************************/
  public static class ArraySizeComparator implements Comparator<Object>
  {
    private boolean ascending;
    /************************************************************************/
    public ArraySizeComparator(boolean ascending)
    {
      this.ascending = ascending;
    }
    /************************************************************************/
    public int compare(Object o1, Object o2)
    {
      Object[] array1 = (Object[]) o1;
      Object[] array2 = (Object[]) o2;
      return Double.compare(array1.length, array2.length) * (ascending ? 1 : -1);
    }
  }
  /***********************************************************************/
  public static class CharacterComparator implements Comparator<Object>, Serializable
  {
    private boolean ascending;
    /************************************************************************/
    public CharacterComparator(boolean ascending)
    {
      this.ascending = ascending;
    }
    /************************************************************************/
    public int compare(Object o1, Object o2)
    {
      Character c1 = (Character) o1;
      Character c2 = (Character) o2;
      return c1.compareTo(c2) * (ascending ? 1 : -1);
    }
  }
  /***********************************************************************/
  public static class StringComparator implements Comparator<Object>, Serializable
  {
    private boolean ascending;
    /************************************************************************/
    public StringComparator(boolean ascending)
    {
      this.ascending = ascending;
    }
    /************************************************************************/
    public int compare(Object o1, Object o2)
    {
      return StringComparator.compare((String) o1, (String) o2, ascending);
    }
    /************************************************************************/
    public static int compare(String o1, String o2, boolean ascending)
    {
      int value = 0;
      if (o1 == null || o2 == null)
      {
        value = compareNull(o1, o2);
      }
      else
      {
        value = o1.compareTo(o2);
      }
      return ascending ? value : -value;
    }
  }
  /************************************************************************/
  /************************************************************************/
}
