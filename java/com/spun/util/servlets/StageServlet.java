package com.spun.util.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface StageServlet
{
  /***********************************************************************/
  public String doStage(int stage, HttpServletRequest req, HttpServletResponse res) throws Throwable;
  /***********************************************************************/
  public String processError(Throwable t, HttpServletRequest req);
  /***********************************************************************/
  /***********************************************************************/
}
