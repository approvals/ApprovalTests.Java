package com.spun.util;

import java.util.Comparator;

public class EmbeddedSorter <T>
  implements java.util.Comparator<T>
{
  private MethodExecutionPath executionPath;
  private Comparator comparator;
  
  /************************************************************************/
  public EmbeddedSorter(Comparator comparator, MethodExecutionPath executionPath)
  {
    this.comparator = comparator;
    this.executionPath = executionPath;
  }
  /************************************************************************/
  public int compare(T o1, T o2)
  {
    Object e1 = executionPath.extractValue(o1);
    Object e2 = executionPath.extractValue(o2);
    return comparator.compare(e1, e2);
  }
  /************************************************************************/
  /************************************************************************/
}
