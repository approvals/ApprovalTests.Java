package org.approvaltests.combinations.pairwise;

import java.util.Arrays;

public class Parameter<T> implements Parameter1
{
  private final String name;
  private final T[] values;
  public Parameter(int position, T... values)
  {
    this.name = "" + (position + 1);
    this.values = values;
  }
  public String getName()
  {
    return name;
  }
  @Override
  public T[] toArray()
  {
    return values;
  }
  @Override
  public int size()
  {
    return values.length;
  }
  @Override
  public T get(int index)
  {
    return values[index];
  }
  @Override
  public String toString()
  {
    return name + ": " + Arrays.toString(this.values);
  }
}
