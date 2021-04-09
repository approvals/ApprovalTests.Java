package org.approvaltests.legacycode;

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
}
