package com.spun.util.servlets;


public class ExpiredSessionError
  extends RuntimeException
{

  private String htmlText;

  /***********************************************************************/
  public ExpiredSessionError()
  {
    htmlText = "<HTML><H1>You are trying to reload an expired page</H1></HTML>";
  }
  /***********************************************************************/
  public void setHTMLText(String htmlText)
  {
    this.htmlText = htmlText;
  }
  /***********************************************************************/
  public String getHTMLText()
  {
    return htmlText;
  }
  /***********************************************************************/
  /***********************************************************************/
}
