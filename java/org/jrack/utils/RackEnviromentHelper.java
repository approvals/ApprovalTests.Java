package org.jrack.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jrack.RackEnvironment;

import com.spun.util.servlets.BasicServlet;

public class RackEnviromentHelper
{
  private final Map<String, Object> environment;
  public RackEnviromentHelper(Map<String, Object> environment)
  {
    this.environment = environment;
  }
  public String load(String key)
  {
    return BasicServlet.loadNullableString(getRequest(), key);
  }
  public HttpServletRequest getRequest()
  {
    return (HttpServletRequest) environment.get("HttpServletRequest");
  }
  public static String getPathInfo(Map<String, Object> input)
  {
    String clazz = (String) input.get(RackEnvironment.PATH_INFO);
    if (clazz == null)
    {
      clazz = (String) input.get("REQUEST_PATH");
    }
    return clazz;
  }
}
