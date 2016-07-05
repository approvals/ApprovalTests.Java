package com.spun.util.servlets.tests;

import com.spun.util.logger.SimpleLogger;
import com.spun.util.servlets.ServletSynchronizer;
import com.spun.util.servlets.SynchronizedServlet;

public class MockSynchronizedServlet implements SynchronizedServlet
{
  public static int           counter = 0;
  private ServletSynchronizer sync;
  private String              key;
  /***********************************************************************/
  public void init(ServletSynchronizer sync, String key)
  {
    this.sync = sync;
    this.key = key;
  }
  /***********************************************************************/
  public void run()
  {
    try
    {
      counter++;
      Thread.sleep(500);
      sync.servletFinished("Run " + counter + " time(s)", key);
    }
    catch (Throwable e)
    {
      SimpleLogger.warning(e);
      sync.servletFinished(e, key);
    }
  }
  /***********************************************************************/
  /***********************************************************************/
}
