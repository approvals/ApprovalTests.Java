package com.spun.util.tests;

import com.spun.util.logger.SimpleLogger;

public final class TimeOut implements Runnable
{
  private long time;
  /***********************************************************************/
  public TimeOut(long time)
  {
    this.time = time;
    new Thread(this).start();
  }
  /***********************************************************************/
  public void run()
  {
    SimpleLogger.event("launched");
    try
    {
      Thread.sleep(time);
    }
    catch (InterruptedException e)
    {
      SimpleLogger.warning(e);
    }
    System.exit(0);
  }
  /***********************************************************************/
  /***********************************************************************/
}
