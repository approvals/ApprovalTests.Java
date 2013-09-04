package org.teachingextensions.logo;

import java.util.ArrayList;
import java.util.List;

import com.spun.util.NumberUtils;

/**
 * A Wheel is a List with no ending on beginning <br/>
 * 
 * <b>Example:</b> {@code  
 *    Wheel<String> names = new Wheel<String>();  
 *    names.add("Chocolate"); 
 *    names.add("Peanut Butter"); 
 *    for(int i = 0; i < 6; i++)  > {
 * String name = names.next();
 * System.out.print(name + " ");
 * }
 * }
 * Would result in : <br/>
 * Chocolate Peanut Butter Chocolate Peanut Butter Chocolate Peanut Butter
 * 
 * @param <T>
 */
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
    assertNonEmpty();
    if (index >= list.size())
    {
      index = 0;
    }
    T t = list.get(index++);
    return t;
  }
  private void assertNonEmpty()
  {
    if (list.isEmpty())
    {
      String message = "I call shenanigans!!!\nThis Wheel is empty\nYou can NOT get something from the Wheel before you've added anything to it.";
      throw new RuntimeException(message);
    }
  }
  public T getRandomFrom()
  {
    assertNonEmpty();
    int index = NumberUtils.getRandomInt(0, list.size());
    return list.get(index);
  }
  public void empty()
  {
    list.clear();
    index = 0;
  }
}
