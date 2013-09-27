package org.jrack.tests;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.EnumerationUtils;

public class ReportEnvironmentServer extends HttpServlet
{
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
    resp.setContentType("text/html");
    ServletOutputStream out = resp.getOutputStream();
    List<String> list = EnumerationUtils.toList(req.getHeaderNames());
    for (String key : list)
    {
      out.print(String.format("%s = %s <br>", key, req.getHeader(key)));
    }
    out.flush();
  }
}
