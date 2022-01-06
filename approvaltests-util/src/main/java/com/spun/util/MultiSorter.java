package com.spun.util;

import java.util.Comparator;

public class MultiSorter<T> implements java.util.Comparator<T>
{
  private Comparator<T>[] comparators = null;
  public MultiSorter(Comparator<T>... comparators)
  {
    this.comparators = comparators;
  }
  public int compare(T o1, T o2)
  {
    for (Comparator<T> comp : comparators)
    {
      int value = comp.compare(o1, o2);
      if (value != 0)
      { return value; }
    }
    return 0;
  }
}
