/**
 * 
 */
package com.spun.util.io;

public class StackElementLevelSelector implements StackElementSelector
{
  private final int ignoreLevels;
  public StackElementLevelSelector(int ignoreLevels)
  {
    this.ignoreLevels = ignoreLevels;
  }
  public StackTraceElement selectElement(StackTraceElement[] trace)
  {
    return trace[ignoreLevels + 1];
  }
}