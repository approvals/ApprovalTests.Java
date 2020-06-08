package com.spun.util;

public class Threads
{
  private int threadCount = 0;
  /************************************************************************/
  public Threads()
  {
    ThreadGroup place = Thread.currentThread().getThreadGroup();
    while (place.getParent() != null)
    {
      place = place.getParent();
    }
    threadCount = place.activeCount();
  }
  /************************************************************************/
  public int getThreadCount()
  {
    return threadCount;
  }
  
  
}