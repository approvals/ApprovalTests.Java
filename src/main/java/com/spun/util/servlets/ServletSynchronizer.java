package com.spun.util.servlets;

import java.util.Hashtable;

/**
 * This is the top level servlet which all others extend.
 **/
public class ServletSynchronizer
{
  private Hashtable<String, PassThrough> keyHolder = new Hashtable<String, PassThrough>();
  private long                           timeOut   = 120000;
  /***********************************************************************/
  public synchronized void queueServlet(PassThrough pass)
  {
    PassThrough old = (PassThrough) keyHolder.put(pass.getKey(), pass);
    if (old != null)
    {
//      My_System.event("Aborted on key '" + pass.getKey() + "'");
      old.setResponse("Aborted");
    }
    if ((old == null) || ((System.currentTimeMillis() - old.getCreationTime()) > timeOut))
    {
      pass.getSynchronizedServlet().init(this, pass.getKey());
//      My_System.event("Started on Key '" + pass.getKey() + "'");
      new Thread(pass.getSynchronizedServlet()).start();
    }
  }
  /***********************************************************************/
  /**
   * To pass on the response
   **/
  public synchronized void servletFinished(String response, String key)
  {
    PassThrough pass = (PassThrough) keyHolder.remove(key);
    pass.setResponse(response);
  }
  /***********************************************************************/
  /**
   * To pass on the response
   **/
  public synchronized void servletProgressReport(double amountDone, String key)
  {
    PassThrough pass = (PassThrough) keyHolder.get(key);
    pass.doProgressReport(amountDone);
  }
  /***********************************************************************/
  /**
   * To pass on a exception
   **/
  public synchronized void servletFinished(Throwable error, String key)
  {
    PassThrough pass = (PassThrough) keyHolder.remove(key);
    pass.setError(error);
  }
  /***********************************************************************/
  /***********************************************************************/
}