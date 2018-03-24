package com.spun.util.filters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.lambda.query.Query;

/**
 * Listens to the state of a EnabledConditions object
 **/
public class FilterUtils
{
  /***********************************************************************/
  /**
   * @return a new List containing all elements of the list for which isExtracted() would return true
   **/
  public static <T> List<T> retainExtracted(Collection<? extends T> fromList, Filter filter)
      throws IllegalArgumentException
  {
    return filter(fromList, filter, true);
  }
  /***********************************************************************/
  /**
   * @return a new List containing all elements of the list for which isExtracted() would return false
   **/
  public static <T> List<T> retainPurified(List<? extends T> fromList, Filter filter)
      throws IllegalArgumentException
  {
    return filter(fromList, filter, false);
  }
  /***********************************************************************/
  /**
   * @return a new List containing all elements of the list for which isExtracted() would return true
   **/
  public static <T> List<T> retainExtracted(T fromObjects[], Filter<T> filter) throws IllegalArgumentException
  {
    Filter<T> filter2 = checkNull(filter);
    return Query.where(fromObjects, t -> filter2.isExtracted(t));
  }
  /***********************************************************************/
  /**
   * @return a new List containing all elements of the list for which isExtracted() would return true
   **/
  private static <T> List<T> filter(Iterable<? extends T> collection, Filter filter, boolean retainExtracted)
      throws IllegalArgumentException
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
  public static <T> List<T> retainPurified(T fromObjects[], Filter<T> filter) throws IllegalArgumentException
  {
    return filter(Arrays.asList(fromObjects), checkNull(filter), false);
  }
  private static <T> Filter<T> checkNull(Filter<T> filter)
  {
    return filter == null ? t -> true : filter;
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