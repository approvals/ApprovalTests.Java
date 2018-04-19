package com.spun.util.servlets;

public interface SynchronizedServlet 
	extends Runnable
{
  /***********************************************************************/
	/** 
	  * This should run the servlet. when it is completed, it should make a call 
		* back to ServletSynchronizer.servletFinished(key, result)
		* @see com.spun.util.servlets.ServletSynchronizer.servletFinished(String,String)
		**/
  public void init(ServletSynchronizer sync, String key);
  
  /***********************************************************************/
  /***********************************************************************/
}