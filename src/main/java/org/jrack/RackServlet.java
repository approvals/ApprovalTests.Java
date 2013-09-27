package org.jrack;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spun.util.ObjectUtils;

public class RackServlet extends HttpServlet
{
  private static JRack defaultRack;
  private final JRack  rack;
  public RackServlet()
  {
    this(defaultRack);
  }
  public RackServlet(JRack rack)
  {
    this.rack = rack;
  }
  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
    processCall(req, resp);
  }
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
    processCall(req, resp);
  }
  private void processCall(HttpServletRequest req, HttpServletResponse resp) throws IOException
  {
    try
    {
      RackResponse response = rack.call(getEnvironment(req));
      writeResponse(resp, response);
    }
    catch (Exception e)
    {
      ObjectUtils.throwAsError(e);
    }
  }
  private void writeResponse(HttpServletResponse resp, RackResponse response) throws IOException
  {
    resp.setStatus(response.getStatus());
    if (response.getHeaders() != null)
    {
      for (String key : response.getHeaders().keySet())
      {
        resp.setHeader(key, response.getHeaders().get(key));
      }
    }
    resp.getWriter().print(response.getResponse());
  }
  private Map<String, Object> getEnvironment(HttpServletRequest req)
  {
    Map<String, Object> environment = new HashMap<String, Object>();
    environment.put(RackEnvironment.REQUEST_METHOD, req.getMethod());
    environment.put(RackEnvironment.PATH_INFO, req.getPathInfo());
    environment.put(RackEnvironment.QUERY_STRING, req.getQueryString());
    environment.put(RackEnvironment.SERVER_NAME, req.getServerName());
    environment.put(RackEnvironment.SERVER_PORT, req.getServerPort());
    environment.put(RackEnvironment.SCRIPT_NAME, req.getServletPath());
    environment.put("HTTP_ACCEPT_ENCODING", req.getHeader("Accept-Encoding"));
    environment.put("HTTP_USER_AGENT", req.getHeader("User-Agent"));
    environment.put("HTTP_HOST", req.getHeader("Host"));
    environment.put("HTTP_CONNECTION", req.getHeader("Connection"));
    environment.put("HTTP_ACCEPT", req.getHeader("Accept"));
    environment.put("HTTP_ACCEPT_CHARSET", req.getHeader("Accept-Charset"));
    environment.put("REMOTE_ADDR", req.getRemoteAddr());
    environment.put("REMOTE_HOST", req.getRemoteHost());
    environment.put("REMOTE_USER", req.getRemoteUser());
    environment.put("REQUEST_PATH", req.getRequestURI());
    environment.put("REQUEST_URL", req.getPathTranslated());
    environment.put("HTTP_KEEP_ALIVE", req.getHeader("Keep-Alive"));
    environment.put("HTTP_VERSION", req.getProtocol());
    environment.put("SERVER_PROTOCOL", req.getProtocol());
    environment.put("HttpServletRequest", req);
    return environment;
  }
  @Override
  protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
    // TODO Auto-generated method stub
    super.doHead(req, resp);
  }
  @Override
  protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
    processCall(req, resp);
  }
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
    processCall(req, resp);
  }
  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
    processCall(req, resp);
  }
  @Override
  protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
    processCall(req, resp);
  }
  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
    processCall(req, resp);
  }
  public static void setDefaultRack(JRack rack)
  {
    RackServlet.defaultRack = rack;
  }
}
