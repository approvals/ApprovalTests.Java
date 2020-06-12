package com.spun.util;

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
