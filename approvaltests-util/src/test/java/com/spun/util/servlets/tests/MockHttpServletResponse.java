package com.spun.util.servlets.tests;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Llewellyn Falco
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MockHttpServletResponse implements HttpServletResponse
{
  ArrayList<Cookie> cookies = new ArrayList<Cookie>();
  private ByteArrayOutputStream output = new ByteArrayOutputStream();
  private ServletOutputStream outputStream = new MockServletOutputStream(output);
  
  public Cookie[] getCookies()
  {
    return (Cookie[]) cookies.toArray(new Cookie[0]);
  }
  
  public void addCookie(Cookie arg0)
  {
    cookies.add(arg0);
  }
  
  public boolean containsHeader(String arg0) { return false; }
  
  public String encodeURL(String arg0){ return null; }
  
  public String encodeRedirectURL(String arg0){ return null; }
  
  public String encodeUrl(String arg0){ return null; }
  
  public String encodeRedirectUrl(String arg0){ return null; }
  
  public void sendError(int arg0, String arg1) throws IOException{}
  
  public void sendError(int arg0) throws IOException{}  
    
  public void sendRedirect(String arg0) throws IOException{}
  
  public void setDateHeader(String arg0, long arg1){}  
  
  public void addDateHeader(String arg0, long arg1){}
  
  public void setHeader(String arg0, String arg1){}
  
  public void addHeader(String arg0, String arg1){}
  
  public void setIntHeader(String arg0, int arg1){}
  
  public void addIntHeader(String arg0, int arg1){}
  
  public void setStatus(int arg0){}
  
  public void setStatus(int arg0, String arg1){}
  
  public String getCharacterEncoding(){ return null; }
  
  public ServletOutputStream getOutputStream() throws IOException{ return outputStream; }
  public byte[] getOutput() { return output.toByteArray(); }
  
  public PrintWriter getWriter() throws IOException{ return null; }
  
  public void setContentLength(int arg0){}
  
  public void setContentType(String arg0){}
  
  public void setBufferSize(int arg0){}
  
  public int getBufferSize(){ return 0; }
  
  public void flushBuffer() throws IOException{}
  
  public void resetBuffer(){}
  
  public boolean isCommitted(){ return false; }
  
  public void reset(){}
  
  public void setLocale(Locale arg0){}
  
  public Locale getLocale(){ return null; }
  
  public String getContentType()
  {
    return null;
  }
  public void setCharacterEncoding(String arg0)
  {
    
  }  
}
