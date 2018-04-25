package com.spun.util;

public class ThreadUtils
{
  public static void sleep(long millis)
  {
    try
    {
      Thread.sleep(millis);
    }
    catch (Exception e)
    {
      // ignore
    }
  }
  public static StackTraceElement[] getStackTrace()
  {
    return Thread.currentThread().getStackTrace();
  }
}
