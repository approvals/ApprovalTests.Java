package org.approvaltests;

import java.util.ArrayList;

public class StackListings<T>
{
  private ArrayList<T> methods = new ArrayList<>();
  private ArrayList<T> classes = new ArrayList<>();
  public void addToMethodList(T found)
  {
    methods.add(found);
  }
  public void addToClassList(T found)
  {
    classes.add(found);
  }
  @Override
  public String toString()
  {
    return String.format("StackListings [\n  methods=%s\n  classes=%s\n]", methods, classes);
  }
  public T getFirst()
  {
    if (!methods.isEmpty())
    {
      return methods.get(0);
    }
    else if (!classes.isEmpty()) { return classes.get(0); }
    return null;
  }
}
