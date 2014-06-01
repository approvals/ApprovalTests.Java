package com.spun.util.tests;

import com.spun.util.MySystem;

public final class TimeOut
  implements Runnable
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
    MySystem.event("launched");
    try
    {
      Thread.sleep(time);
    }
    catch (InterruptedException e)
    {
      MySystem.warning(e);
    }
    System.exit(0);
  }
  /***********************************************************************/
  /***********************************************************************/
}
