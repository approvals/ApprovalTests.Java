package com.spun.util.logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spun.util.servlets.BasicServlet;
import com.spun.util.servlets.StageServlet;
import com.spun.util.servlets.StageServletUtils;

public class JavascriptLogger extends BasicServlet implements StageServlet
{
  /***********************************************************************/
  public void doGet(HttpServletRequest req, HttpServletResponse res)
  {
    StageServletUtils.doStageServlet(this, req, res);
  }
  /***********************************************************************/
  public String doStage(int stage, HttpServletRequest req, HttpServletResponse res) throws Throwable
  {
    switch (stage)
    {
      case 1 :
        return doStage1log(req);
      default :
        throw new Error("Stage " + stage + " is unknown");
    }
  }
  /***********************************************************************/
  private String doStage1log(HttpServletRequest req)
  {
    String browser = loadNullableString(req, "browser");
    String version = loadNullableString(req, "version");
    String platform = loadNullableString(req, "platform");
    String expression = loadNullableString(req, "expression");
    String errorType = loadNullableString(req, "errorType");
    String errorCode = loadNullableString(req, "errorCode");
    String errorMessage = loadNullableString(req, "errorMessage");
    String bestCodeGuess = loadNullableString(req, "bestCodeGuess");
    String url = loadNullableString(req, "url");
    SimpleLogger.variable("url", url);
    SimpleLogger.variable("browser", browser);
    SimpleLogger.variable("version", version);
    SimpleLogger.variable("platform", platform);
    SimpleLogger.variable("expression", expression);
    SimpleLogger.variable("errorType", errorType);
    SimpleLogger.variable("errorCode", errorCode);
    SimpleLogger.variable("errorMessage", errorMessage);
    SimpleLogger.variable("bestCodeGuess", bestCodeGuess);
    return "<logReport/>";
  }
  /***********************************************************************/
  /***********************************************************************/
  @Override
  protected String getLogFile()
  {
    return null;
  }
}