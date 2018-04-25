package com.spun.util.servlets.tests;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.spun.util.StringUtils;

public class MockHttpSession implements HttpSession
{
  /***********************************************************************/
  private HashMap<String, Object> table = new HashMap<>();
  public long getCreationTime()
  {
    return 0;
  }
  /***********************************************************************/
  public String getId()
  {
    return "id";
  }
  /***********************************************************************/
  public long getLastAccessedTime()
  {
    return 0;
  }
  /***********************************************************************/
  public ServletContext getServletContext()
  {
    return null;
  }
  /***********************************************************************/
  public void setMaxInactiveInterval(int arg0)
  {
  }
  /***********************************************************************/
  public int getMaxInactiveInterval()
  {
    return 0;
  }
  /***********************************************************************/
  @Deprecated
  public javax.servlet.http.HttpSessionContext getSessionContext()
  {
    return null;
  }
  /***********************************************************************/
  public Object getAttribute(String arg0)
  {
    return table.get(arg0);
  }
  /***********************************************************************/
  public Object getValue(String arg0)
  {
    return table.get(arg0);
  }
  /***********************************************************************/
  public Enumeration<Object> getAttributeNames()
  {
    return null;
  }
  /***********************************************************************/
  public String[] getValueNames()
  {
    return StringUtils.toArray(table.keySet());
  }
  /***********************************************************************/
  public void setAttribute(String arg0, Object arg1)
  {
    table.put(arg0, arg1);
  }
  /***********************************************************************/
  public void putValue(String arg0, Object arg1)
  {
    table.put(arg0, arg1);
  }
  /***********************************************************************/
  public void removeAttribute(String arg0)
  {
    table.remove(arg0);
  }
  /***********************************************************************/
  public void removeValue(String arg0)
  {
  }
  /***********************************************************************/
  public void invalidate()
  {
  }
  /***********************************************************************/
  public boolean isNew()
  {
    return false;
  }
  /***********************************************************************/
  /***********************************************************************/
}
