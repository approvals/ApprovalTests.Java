package org.approvaltests.combinations.pairwise;

public class Parameter<T> implements Parameter1
{

  private T[] arr;
  public String getName()
  {
    return name;
  }

  @Override public T[] toArray() {
    return arr;
  }

  @Override public int size() {
    return arr.length;
  }

  @Override public T get(int index) {
    return arr[index];
  }

  public void setName(String name)
  {
    this.name = name;
  }
  private String name;
  public Parameter()
  {
    super();
  }
  public Parameter(String name)
  {
    this.name = name;
  }
  public Parameter(int position, T... values)
  {
    this.name = "" + (position + 1);
    this.arr = values;
  }
  @Override
  public String toString()
  {
    return name + ": " + super.toString();
  }
}
