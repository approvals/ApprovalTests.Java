package com.spun.util;

import com.spun.util.logger.SimpleLogger;
import org.lambda.functions.Function0;

public class TimedObject
{
  private Object object           = null;
  private long   timeOutInMillis  = 1000;
  private long   lastTimeAccessed = 0;
  private Function0<Long> currentTime;
  // TODO: consider Duration object
  public TimedObject(long timeOutInMillis)
  {
    this(timeOutInMillis, System::currentTimeMillis);
  }
  public TimedObject(long timeOutInMillis, Function0<Long> currentTime)
  {
    this.timeOutInMillis = timeOutInMillis;
    this.currentTime = currentTime;
  }

  public Object get()
  {
    touched();
    return object;
  }
  private synchronized void touched()
  {
    try
    {
      boolean launch = (lastTimeAccessed == 0);
      this.lastTimeAccessed = currentTime.call();
      if (launch)
      {
        new LambdaThreadLauncher(this::clean);
      }
    }
    catch (Throwable t)
    {
      SimpleLogger.warning(t);
    }
  }
  public void clean()
  {
    try
    {
      while (currentTime.call() < (lastTimeAccessed + timeOutInMillis))
      {
        long diff = (lastTimeAccessed + timeOutInMillis) - currentTime.call();
        diff = Math.max(diff, 1);
        Thread.sleep(diff);
      }
    }
    catch (Throwable t)
    {
      SimpleLogger.warning(t);
    }
    synchronized (this)
    {
      this.object = null;
      this.lastTimeAccessed = 0;
    }
  }
  public void set(Object object)
  {
    touched();
    this.object = object;
  }
}
