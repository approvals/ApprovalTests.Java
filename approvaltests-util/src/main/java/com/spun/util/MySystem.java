package com.spun.util;

import com.spun.util.logger.SimpleLogger;

/**
 * @deprecated replaced by {@link SimpleLogger}
 **/
public class MySystem
{
  public static void toggleAll(boolean t)
  {
    SimpleLogger.toggleAll(t);
  }
  public static void setHourGlassWrap(int numberOfDots)
  {
    SimpleLogger.setHourGlassWrap(numberOfDots);
  }
  public static void hourGlass()
  {
    SimpleLogger.hourGlass();
  }
  /**
   * @deprecated replaced by {@link SimpleLogger#useMarkers()}
   */
  public synchronized static void markerIn()
  {
  }
  /**
   * @deprecated replaced by {@link SimpleLogger#useMarkers()}
   */
  public synchronized static void markerOut(String text)
  {
  }
  /**
   * @deprecated replaced by {@link SimpleLogger#query(String)}
   */
  public synchronized static void query(String sqlQuery)
  {
    SimpleLogger.query(sqlQuery);
  }
  /**
   * @deprecated replaced by {@link SimpleLogger#query(String)}
   */
  public synchronized static void query(String queryName, Object sqlQuery)
  {
    SimpleLogger.query(queryName, sqlQuery);
  }
  /**
   * @deprecated replaced by {@link SimpleLogger#variable(String)}
   */
  public synchronized static void variable(String statement)
  {
    SimpleLogger.variable(statement);
  }
  public static void variableFormated(String string, Object... parameters)
  {
    SimpleLogger.variableFormated(string, parameters);
  }
  public synchronized static void variable(String name, Object value)
  {
    SimpleLogger.variable(name, value);
  }
  public synchronized static void variable(String name, Object array[])
  {
    SimpleLogger.variable(name, array);
  }
  public synchronized static void variable(Object array[])
  {
    SimpleLogger.variable(array);
  }
  public synchronized static void message(String text)
  {
    SimpleLogger.message(text);
  }
  public static void event(String text)
  {
    SimpleLogger.event(text);
  }
  public synchronized static void warning(String text)
  {
    SimpleLogger.warning(text);
  }
  public synchronized static void warning(Throwable throwable)
  {
    SimpleLogger.warning(throwable);
  }
  public synchronized static void warning(String statement, Throwable throwable)
  {
    SimpleLogger.warning(statement, throwable);
  }
  /**
   * Returns all the Thread output of a group as a string.
   * @return A List of The thread, and if it is alive and/or interrupted.
   **/
  public static String dumpAllThreadsInGroup(ThreadGroup tg)
  {
    Thread[] threads = new Thread[tg.activeCount()];
    tg.enumerate(threads);
    StringBuffer s = new StringBuffer("Active Thread Dump\n");
    for (int x = 0; x < threads.length; x++)
    {
      if (threads[x] != null)
      {
        s.append("thread=" + threads[x] + ", isAlive=" + threads[x].isAlive() + ", isInterrupted="
            + threads[x].isInterrupted() + "\n");
      }
    }
    return s.toString();
  }
  /**
    * @deprecated replaced by {@link SimpleLogger#logMemoryStatus()}
  */
  public static void dumpMemory()
  {
    SimpleLogger.logMemoryStatus();
  }
  /**
   * Dumps the current thread status for ALL groups.
   **/
  public static String dumpAllThreads()
  {
    ThreadGroup place = Thread.currentThread().getThreadGroup();
    while (place.getParent() != null)
    {
      place = place.getParent();
    }
    //place.list();
    return dumpAllThreadsInGroup(place);
  }
}