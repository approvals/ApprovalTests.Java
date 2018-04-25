package com.spun.util.servlets;

import javax.servlet.http.HttpServletRequest;



public class PassThrough 
{
  private String key = null;
  private SynchronizedServlet servlet= null;
	private String htmlText = null;
	private Throwable error = null;
	private long created = 0;
  private ProgressBar progressBar;
  /***********************************************************************/
  public PassThrough(HttpServletRequest req, SynchronizedServlet servlet) 
  {
    this(req, servlet, null);
  }
  /***********************************************************************/
  public PassThrough(HttpServletRequest req, SynchronizedServlet servlet, ProgressBar progressBar) 
  {
    try
    {
      this.key = req.getSession().getId();
    }
    catch(java.lang.NoSuchMethodError e)
    {
      this.key = req.getRemoteAddr(); // backwards compatability.
    }
    this.progressBar = progressBar;
		this.servlet = servlet;
    created = System.currentTimeMillis();
  }
  /***********************************************************************/
  public PassThrough(String key, SynchronizedServlet servlet) 
  {
    this.key = key;
		this.servlet = servlet;
    created = System.currentTimeMillis();
  }
  /***********************************************************************/
	public SynchronizedServlet getSynchronizedServlet()
	{
		return servlet;
	}
  /***********************************************************************/
  public String getKey()
  {
  	return key;
  }
  /***********************************************************************/
  public long getCreationTime()
  {
  	return created;
  }
  /***********************************************************************/
  public String getResponse()
		throws InterruptedException, Throwable
  {
  	while (htmlText == null)
  	{
  		Thread.sleep(200);
			if (error != null)
			{
			  if (progressBar != null)
			  {
			    progressBar.doFinish();
			  }
				throw error;
			}
  	}
	  if (progressBar != null)
	  {
	    progressBar.doFinish();
	  }
  	return htmlText;
  
  }
  /***********************************************************************/
  public void setResponse(String text)
  {
  	this.htmlText = text;
  }
  /***********************************************************************/
  public void setError(Throwable error)
  {
  	this.error = error;
  }
  /***********************************************************************/
  public void doProgressReport(double amountDone)
  {
    if (progressBar != null)
    {
      progressBar.doProgressReport(amountDone);
    }
  }
	
  /***********************************************************************/
  /***********************************************************************/
}