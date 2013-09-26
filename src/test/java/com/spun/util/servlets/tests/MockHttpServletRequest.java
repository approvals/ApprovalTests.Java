package com.spun.util.servlets.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.spun.util.StringUtils;

public class MockHttpServletRequest implements HttpServletRequest
{
  public Map                parameters = new HashMap<String, Object>();
  private String            body;
  private ArrayList<Cookie> cookies    = new ArrayList<Cookie>();
  /***********************************************************************/
  public MockHttpServletRequest()
  {
    this.setSession(new MockHttpSession());
  }
  /***********************************************************************/
  public MockHttpServletRequest(String params) throws UnsupportedEncodingException
  {
    this.setSession(new MockHttpSession());
    parseParameters(params);
  }
  /***********************************************************************/
  private void parseParameters(String params) throws UnsupportedEncodingException
  {
    if (params == null) { return; }
    params = URLDecoder.decode(params, "UTF-8");
    String pairs[] = StringUtils.split(params, "&");
    for (String pair : pairs)
    {
      String[] t = StringUtils.split(pair, "=");
      setParameter(t[0], t[1]);
    }
  }
  /***********************************************************************/
  public void sync(MockHttpServletResponse response)
  {
    this.cookies = response.cookies;
  }
  /***********************************************************************/
  public void setParameter(String key, String value)
  {
    Object current = parameters.get(key);
    if (current == null)
    {
      parameters.put(key, value);
    }
    else if (current instanceof String)
    {
      ArrayList<String> list = new ArrayList<String>();
      list.add((String) current);
      list.add(value);
      parameters.put(key, list);
    }
    else if (current instanceof ArrayList)
    {
      ((ArrayList<String>) current).add(value);
    }
  }
  /***********************************************************************/
  public String getParameter(String key)
  {
    return (String) this.parameters.get(key);
  }
  /***********************************************************************/
  public Enumeration getParameterNames()
  {
    return new Enumeration()
    {
      int count = 0;
      public boolean hasMoreElements()
      {
        return count < parameters.size();
      }
      public Object nextElement()
      {
        synchronized (MockHttpServletRequest.this)
        {
          if (count < parameters.size()) { return parameters.keySet().toArray()[count++]; }
        }
        throw new NoSuchElementException("Vector Enumeration");
      }
    };
  }
  /***********************************************************************/
  public Map getParameterMap()
  {
    return parameters;
  }
  /***********************************************************************/
  public Cookie[] getCookies()
  {
    return (Cookie[]) cookies.toArray(new Cookie[cookies.size()]);
  }
  /***********************************************************************/
  public void setCookies(Cookie[] cookies)
  {
    this.cookies.addAll(Arrays.asList(cookies));
  }
  /***********************************************************************/
  public String getMethod()
  {
    return null;
  }
  public String getRequestURI()
  {
    return null;
  }
  public String getServletPath()
  {
    return null;
  }
  public String getPathInfo()
  {
    return null;
  }
  public String getPathTranslated()
  {
    return null;
  }
  public String getQueryString()
  {
    return null;
  }
  public String getRemoteUser()
  {
    return null;
  }
  public String getAuthType()
  {
    return null;
  }
  public String getHeader(String name)
  {
    return null;
  }
  public int getIntHeader(String name)
  {
    return 0;
  }
  public long getDateHeader(String name)
  {
    return 0;
  }
  public Enumeration getHeaderNames()
  {
    return new StringTokenizer("");
  }
  public HttpSession getSession(boolean create)
  {
    return session;
  }
  public String getRequestedSessionId()
  {
    return null;
  }
  public boolean isRequestedSessionIdValid()
  {
    return false;
  }
  public boolean isRequestedSessionIdFromCookie()
  {
    return false;
  }
  public boolean isRequestedSessionIdFromUrl()
  {
    return false;
  }
  public int getContentLength()
  {
    return body == null ? 0 : body.length();
  }
  public String getContentType()
  {
    return null;
  }
  public String getProtocol()
  {
    return null;
  }
  public String getScheme()
  {
    return null;
  }
  public String getServerName()
  {
    return null;
  }
  public int getServerPort()
  {
    return 0;
  }
  public String getRemoteAddr()
  {
    return "128.195.24.3";
  }
  public String getRemoteHost()
  {
    return null;
  }
  public String getRealPath(String path)
  {
    return null;
  }
  public ServletInputStream getInputStream() throws IOException
  {
    return null;
  }
  public String[] getParameterValues(String name)
  {
    ArrayList<String> string = (ArrayList<String>) parameters.get(name);
    return string.toArray(new String[string.size()]);
  }
  public Enumeration getAttributeNames()
  {
    return null;
  }
  public Object getAttribute(String name)
  {
    return null;
  }
  public HttpSession getSession()
  {
    return session;
  }
  public BufferedReader getReader() throws IOException
  {
    return new BufferedReader(new StringReader(body));
  }
  public String getCharacterEncoding()
  {
    return null;
  }
  public void setAttribute(String name, Object o)
  {
  }
  public boolean isRequestedSessionIdFromURL()
  {
    return false;
  }
  public void setCharacterEncoding(String c)
  {
  }
  public void removeAttribute(String a)
  {
  }
  public Enumeration getLocales()
  {
    return null;
  }
  public Locale getLocale()
  {
    return null;
  }
  public boolean isSecure()
  {
    return false;
  }
  public RequestDispatcher getRequestDispatcher(String s)
  {
    return null;
  }
  public Enumeration getHeaders(String s)
  {
    return null;
  }
  public String getContextPath()
  {
    return null;
  }
  public boolean isUserInRole(String s)
  {
    return false;
  }
  public Principal getUserPrincipal()
  {
    return null;
  }
  public StringBuffer getRequestURL()
  {
    return null;
  }
  public MockHttpSession session = null;
  /***********************************************************************/
  public void setSession(MockHttpSession session)
  {
    this.session = session;
  }
  /***********************************************************************/
  public void setParameterMap(Map params)
  {
    this.parameters = params;
  }
  /***********************************************************************/
  public void setBody(String body)
  {
    this.body = body;
  }
  /***********************************************************************/
  public String getBody()
  {
    return body;
  }
  public int getRemotePort()
  {
    return 0;
  }
  public String getLocalName()
  {
    return null;
  }
  public String getLocalAddr()
  {
    return null;
  }
  public int getLocalPort()
  {
    return 0;
  }
}