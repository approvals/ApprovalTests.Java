package org.approvaltests.combinations.pairwise;

import java.util.Arrays;

public class Parameter<T> implements Parameter1
{
  private final String name;
  private final T[] arr;
  public Parameter(int position, T... values)
  {
    this.name = "" + (position + 1);
    this.arr = values;
  }
  public String getName()
  {
    return name;
  }
  @Override
  public T[] toArray()
  {
    return arr;
  }
  @Override
  public int size()
  {
    return arr.length;
  }
  @Override
  public T get(int index)
  {
    return arr[index];
  }
  @Override
  public String toString()
  {
    return name + ": " + Arrays.toString(this.arr);
  }
}
