package com.spun.util.filters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Listens to the state of a EnabledConditions object
 **/
public class FilterUtils
{
  /***********************************************************************/
  /**
   * @return a new List containing all elements of the list for which isExtracted() would return true
   **/
  public static <T> ArrayList<T> retainExtracted(Collection<? extends T> fromList, Filter filter) throws IllegalArgumentException
  {
    return filter(fromList, filter, true);
  }
  /***********************************************************************/
  /**
   * @return a new List containing all elements of the list for which isExtracted() would return false
   **/
  public static <T> ArrayList<T> retainPurified(List<? extends T> fromList, Filter filter) throws IllegalArgumentException
  {
    return filter(fromList, filter, false);
  }
  /***********************************************************************/
  /**
   * @return a new List containing all elements of the list for which isExtracted() would return true
   **/
  public static <T> ArrayList<T> retainExtracted(T fromObjects[], Filter filter) throws IllegalArgumentException
  {
    return fromObjects == null ? new ArrayList<T>() : filter(Arrays.asList(fromObjects), filter, true);
  }
  /***********************************************************************/
  /**
   * @return a new List containing all elements of the list for which isExtracted() would return true
   **/
  private static <T> ArrayList<T> filter(Iterable<? extends T> collection, Filter filter, boolean retainExtracted) throws IllegalArgumentException
  {
    ArrayList<T> extracted = new ArrayList<T>();
    if (collection != null)
    {
      for (T t : collection)
      {
        boolean ex = (filter == null || filter.isExtracted(t));
        if (ex == retainExtracted)
        {
          extracted.add(t);
        }
      }
    }
    return extracted;
  }
  /***********************************************************************/
  /**
   * @return a new List containing all elements of the list for which isExtracted() would return false
   **/
  public static <T> ArrayList<T> retainPurified(T fromObjects[], Filter filter) throws IllegalArgumentException
  {
    return filter(Arrays.asList(fromObjects), filter, false);
  }
  /***********************************************************************/
  /*                         INNER CLASSES                               */
  /***********************************************************************/
  public static class FilterNotNull implements Filter
  {
    /***********************************************************************/
    public boolean isExtracted(Object object) throws IllegalArgumentException
    {
      return object != null;
    }
  }
  /***********************************************************************/
  /***********************************************************************/
}