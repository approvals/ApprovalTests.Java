package com.spun.util;

import org.lambda.actions.Action0;

import java.time.Duration;

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

  public static void launch(Action0 action)
  {
    launch(action, Duration.ZERO);
  }

  public static void launch(Action0 action, Duration delay)
  {
    new LambdaThreadLauncher(action, delay);
  }

  public static StackTraceElement[] getStackTrace()
  {
    return Thread.currentThread().getStackTrace();
  }
}
