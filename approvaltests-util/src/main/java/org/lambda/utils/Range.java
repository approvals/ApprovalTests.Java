package org.lambda.utils;

import org.lambda.query.Queryable;

import java.util.ArrayList;

public class Range
{
  public static Integer[] get(int start, int stop)
  {
    ArrayList<Integer> a = new ArrayList<Integer>();
    for (int i = start; i <= stop; i++)
    {
      a.add(i);
    }
    return (Integer[]) a.toArray(new Integer[a.size()]);
  }

  public static Queryable<Integer> getAsQueryable(int start, int stop)
  {
    return Queryable.as(get(start, stop));
  }
}
