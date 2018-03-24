package com.spun.util.tests;

import com.spun.util.logger.SimpleLogger;

public class Lock
{
  public Lock()
  {
    try
    {
      while (true)
      {
        Thread.sleep(100000);
      }
    }
    catch (InterruptedException e)
    {
      SimpleLogger.warning(e);
    }
  }
}
