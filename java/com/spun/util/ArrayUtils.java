package com.spun.util;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.lambda.functions.Function1;

/**
 * A static class of convenience methods for arrays and collections.
 **/
public class ArrayUtils
{
  /************************************************************************/
  public static <T> java.util.Collection<T> addArray(java.util.Collection<T> v, T array[])
  {
    if ((array == null) || (v == null)) { return v; }
    for (int i = 0; i < array.length; i++)
    {
      v.add(array[i]);
    }
    return v;
  }
  public static <T> String toString(T[] values, Function1<T, String> formatter)
  {
    return toString(asList(values), formatter);
  }
  public static <T> List<T> asList(T[] values)
  {
    return values == null ? Collections.emptyList() : Arrays.asList(values);
  }
  public static <T> String toString(Iterable<T> values, Function1<T, String> formatter)
  {
    StringBuffer b = new StringBuffer();
    for (T t : values)
    {
      b.append(formatter.call(t) + "\n");
    }
    return b.toString();
  }
  /***********************************************************************/
  public static Vector toReverseVector(Vector<Object> vector)
  {
    Vector<Object> reverse = new Vector<Object>(vector.size());
    for (int i = vector.size() - 1; i >= 0; i--)
    {
      reverse.add(vector.elementAt(i));
    }
    return reverse;
  }
  /***********************************************************************/
  public static <T> T[] toReverseArray(T[] array)
  {
    for (int i = 0; i < array.length / 2; i++)
    {
      T o1 = array[i];
      int end = array.length - i - 1;
      T o2 = array[end];
      array[i] = o2;
      array[end] = o1;
    }
    return array;
  }
  /***********************************************************************/
  public static <T> T[] addToArray(T[] array, T object)
  {
    Object[] newArray = null;
    if (array == null)
    {
      newArray = (Object[]) Array.newInstance(object.getClass(), 1);
      newArray[0] = object;
    }
    else
    {
      newArray = (Object[]) Array.newInstance(array.getClass().getComponentType(), array.length + 1);
      System.arraycopy(array, 0, newArray, 0, array.length);
      newArray[array.length] = object;
    }
    return (T[]) newArray;
  }
  /***********************************************************************/
  public static <T> T[] getSubsection(T[] array, int startInclusive, int endExclusive)
  {
    int length = endExclusive - startInclusive;
    length = length > array.length ? array.length : length;
    T[] newArray = (T[]) Array.newInstance(array.getClass().getComponentType(), length);
    System.arraycopy(array, startInclusive, newArray, 0, length);
    return newArray;
  }
  /***********************************************************************/
  public static boolean isEmpty(Object[] array)
  {
    return ((array == null) || (array.length == 0));
  }
  /************************************************************************/
  public static boolean isEmpty(Collection<?> collection)
  {
    return ((collection == null) || (collection.size() == 0));
  }
  /***********************************************************************/
  public static <T> T getSingleton(T[] parts)
  {
    if (parts == null) { return null; }
    switch (parts.length)
    {
      case 0 :
        return null;
      case 1 :
        return parts[0];
      default :
        throw new Error("Called with more than one object in the array " + Arrays.asList(parts));
    }
  }
  /***********************************************************************/
  public static <H, T extends H> T getFirst(T[] array, Comparator<H> compartor)
  {
    return get(array, compartor, true);
  }
  /***********************************************************************/
  public static <H, T extends H> T getFirst(Collection<T> array, Comparator<H> sorter)
  {
    return get((T[]) array.toArray(), sorter, true);
  }
  /***********************************************************************/
  public static <T> T getLast(T[] array, Comparator<T> sorter)
  {
    return get(array, sorter, false);
  }
  /***********************************************************************/
  public static <T> T getLast(Collection<T> array, Comparator<T> sorter)
  {
    return get((T[]) array.toArray(), sorter, false);
  }
  /***********************************************************************/
  private static <H, T extends H> T get(T[] array, Comparator<H> sorter, boolean wantFirst)
  {
    if (isEmpty(array)) { return null; }
    T last = array[0];
    for (int i = 1; i < array.length; i++)
    {
      int compare = sorter.compare(last, array[i]);
      if ((wantFirst && compare > 0) || (!wantFirst && compare < 0))
      {
        last = array[i];
      }
    }
    return last;
  }
  /***********************************************************************/
  public static List combineResults(Object[] array, String invokeMethod)
  {
    if (ArrayUtils.isEmpty(array)) { return Collections.EMPTY_LIST; }
    try
    {
      return combineResults(array, ObjectUtils.getGreatestCommonDenominator(array, invokeMethod));
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  /***********************************************************************/
  @SuppressWarnings("rawtypes")
  public static List combineResults(Object[] array, Method method)
  {
    if (isEmpty(array)) { return new ArrayList(0); }
    try
    {
      ArrayList<Object> list = new ArrayList<Object>();
      for (int i = 0; i < array.length; i++)
      {
        addArray(list, (Object[]) method.invoke(array[i], (Object[]) null));
      }
      return list;
    }
    catch (Throwable e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  /***********************************************************************/
  public static Object[] combine(Object[] a, Object[] b)
  {
    if (isEmpty(a) && isEmpty(b))
    {
      if (a != null)
      {
        return (Object[]) Array.newInstance(a.getClass().getComponentType(), 0);
      }
      else if (b != null)
      {
        return (Object[]) Array.newInstance(b.getClass().getComponentType(), 0);
      }
      else
      {
        return new Object[0];
      }
    }
    else if (isEmpty(a))
    {
      return b;
    }
    else if (isEmpty(b))
    {
      return a;
    }
    else
    {
      Object[] newArray = null;
      newArray = (Object[]) Array.newInstance(a.getClass().getComponentType(), a.length + b.length);
      System.arraycopy(a, 0, newArray, 0, a.length);
      System.arraycopy(b, 0, newArray, a.length, b.length);
      return newArray;
    }
  }
  /***********************************************************************/
  public static <T> boolean contains(T[] values, T value)
  {
    for (int i = 0; i < values.length; i++)
    {
      if (value.equals(values[i])) { return true; }
    }
    return false;
  }
  /***********************************************************************/
  public static boolean contains(int[] values, int value)
  {
    for (int i = 0; i < values.length; i++)
    {
      if (value == values[i]) { return true; }
    }
    return false;
  }
  /************************************************************************/
  public static <T> T getLast(List<T> list)
  {
    return list.get(list.size() - 1);
  }
  /************************************************************************/
  public static <K, T> T getDefault(HashMap<K, T> map, K key, T defaultValue)
  {
    T value = map.get(key);
    if (value == null)
    {
      map.put(key, defaultValue);
      value = defaultValue;
    }
    return value;
  }
  /************************************************************************/
  public static <K, V> int countValues(HashMap<K, V> out, V matching)
  {
    return count(matching, out.values());
  }
  /************************************************************************/
  public static <V> int count(V matching, Collection<V> values)
  {
    int count = 0;
    for (V value : values)
    {
      if (matching.equals(value))
      {
        count++;
      }
    }
    return count;
  }
  public static <T> List<T> combine(List<T> list1, List<T> list2)
  {
    List<T> all = new ArrayList<T>();
    all.addAll(list1);
    all.addAll(list2);
    return all;
  }
  public static <T> Iterable<T> asIterable(Iterator<T> iterator)
  {
    return new IterableWrapper<T>(iterator);
  }
  public static class IterableWrapper<T> implements Iterable<T>
  {
    private final Iterator<T> iterator;
    public IterableWrapper(Iterator<T> iterator)
    {
      this.iterator = iterator;
    }
    @Override
    public Iterator<T> iterator()
    {
      return iterator;
    }
  }
}