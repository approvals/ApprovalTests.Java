package com.spun.util.reporter;




/***********************************************************************/
public class LoggingReporter implements Reporter
{

  /***********************************************************************/
  
  public void throwError(Throwable error)
  {
    //My_System.warning(error);
    System.err.println(error.getMessage());
  }
}
