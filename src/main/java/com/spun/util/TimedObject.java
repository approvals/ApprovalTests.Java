package com.spun.util;


public class TimedObject
{
  private Object object          = null;
  private long   timeOutInMillis = 1000;
  private long   lastTimeAccessed        = 0;
  /***********************************************************************/

  public TimedObject(long timeOutInMillis)
  {
    this.timeOutInMillis = timeOutInMillis;
  }
  /***********************************************************************/

  public Object get()
  {
    touched();
    return object;
  }
  /***********************************************************************/
  
  private synchronized void touched()
  {
    try
    {
	    boolean launch = (lastTimeAccessed == 0);
	    this.lastTimeAccessed = System.currentTimeMillis();
	    if (launch)
	    {
	      new ThreadLauncher(this, "clean");
	    }
    }
    catch (Throwable t)
    {
      MySystem.warning(t);
    }
  }
  /***********************************************************************/
  public void clean()
  {
    try
    {
	    while (System.currentTimeMillis() < (lastTimeAccessed + timeOutInMillis))
	    {
	      long diff = (lastTimeAccessed + timeOutInMillis) - System.currentTimeMillis();
	      Thread.sleep(diff);
	    }
    }
    catch (Throwable t)
    {
      MySystem.warning(t);
    }
    synchronized (this)
    {
	    this.object = null;
	    this.lastTimeAccessed = 0;
    }
  }
  /***********************************************************************/

  public void set(Object object)
  {
    touched();
    this.object = object;
  }
  /***********************************************************************/
  /***********************************************************************/

}