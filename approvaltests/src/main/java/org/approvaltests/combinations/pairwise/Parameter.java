package org.approvaltests.combinations.pairwise;

import java.util.Arrays;

public class Parameter<T> {
  private final int position;
  private final T[] values;

  public Parameter(int position, T... values)
  {
    this.position = position;
    this.values = values;
  }
  public String getName()
  {
    return "" + (position + 1);
  }
  public T[] toArray()
  {
    return values;
  }
  public int size()
  {
    return values.length;
  }
  public T get(int index)
  {
    return values[index];
  }
  @Override
  public String toString()
  {
    return position + ": " + Arrays.toString(this.values);
  }
}
