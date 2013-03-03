package org.teachingextensions.logo;

import java.util.ArrayList;
import java.util.List;

public class Wheel<T>
{
  private List<T> list  = new ArrayList<T>();
  private int     index = 0;
  public Wheel(T... loadWith)
  {
    for (T t : loadWith)
    {
      add(t);
    }
  }
  public Wheel()
  {
  }
  public void add(T i)
  {
    list.add(i);
  }
  public T next()
  {
    if (list.isEmpty()) { return null; }
    if (index >= list.size())
    {
      index = 0;
    }
    T t = list.get(index++);
    return t;
  }
  public void empty()
  {
    list.clear();
    index = 0;
  }
}
