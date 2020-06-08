package com.spun.util.servlets;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spun.util.NumberUtils;
import com.spun.util.logger.SimpleLogger;

public class StageServletUtils
{
  public static final String TEXT_HTML = "text/html";
  private static long        lastTime  = 0;
  
  public static void doStageServlet(StageServlet servlet, HttpServletRequest req, HttpServletResponse res)
  {
    doStageServlet(servlet, 1, req, res);
  }
  
  public static void doStageServlet(StageServlet servlet, int defaultStage, HttpServletRequest req,
      HttpServletResponse res)
  {
    ServletOutputStream out = null;
    try
    {
      try
      {
        int stage = NumberUtils.load(req.getParameter("loadStage"), defaultStage);
        String html = servlet.doStage(stage, req, res);
        if (html != null)
        {
          res.setContentType(TEXT_HTML); // Required for HTTP
          out = res.getOutputStream();
          out.println(html);
        }
      }
      catch (ExpiredSessionError e)
      {
        out.println(e.getHTMLText());
      }
      catch (Throwable t)
      {
        out.println(servlet.processError(t, req));
      }
      finally
      {
        if (out != null)
        {
          out.close();
        }
      }
    }
    catch (Throwable t2)
    {
      SimpleLogger.warning(t2);
    }
    garbageCollect();
  }
  public static void garbageCollect()
  {
    if (30000 > (System.currentTimeMillis() - lastTime))
    {
      System.gc();
      lastTime = System.currentTimeMillis();
    }
  }
  
  
}
