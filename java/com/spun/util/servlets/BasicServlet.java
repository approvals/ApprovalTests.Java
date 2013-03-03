package com.spun.util.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.spun.util.DateUtils;
import com.spun.util.MySystem;
import com.spun.util.NumberUtils;
import com.spun.util.ObjectUtils;
import com.spun.util.StringUtils;
import com.spun.util.parser.TemplateError;
import com.spun.util.velocity.ParseCall;
import com.spun.util.velocity.VelocityParser;

/**
 * This is the top level servlet which all others extend.
 **/
public class BasicServlet extends HttpServlet
{
  private TemplateError         error           = null;
  private static ServletContext servletContext;
  protected Throwable           connectionError = null;
  /***********************************************************************/
  @Override
  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
    try
    {
      MySystem.useOutputFile(getLogFile(), true);
      servletContext = getServletContext();
    }
    catch (Exception e)
    {
      // just in case
    }
  }
  public static ServletContext getContext()
  {
    return servletContext;
  }
  /***********************************************************************/
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
  {
    doGet(req, res);
  }
  /***********************************************************************/
  public static int load(HttpServletRequest req, String key, int defaultValue)
  {
    return NumberUtils.load(req.getParameter(key), defaultValue);
  }
  /***********************************************************************/
  public static int load(String i, int defaultValue)
  {
    return NumberUtils.load(i, defaultValue);
  }
  /***********************************************************************/
  public static double load(HttpServletRequest req, String key, double defaultValue)
  {
    return NumberUtils.load(req.getParameter(key), defaultValue);
  }
  /***********************************************************************/
  public static double load(String i, double defaultValue)
  {
    return NumberUtils.load(i, defaultValue);
  }
  /***********************************************************************/
  public static boolean loadCheckBox(String i, boolean d)
  {
    return (i == null) ? d : "on".equalsIgnoreCase(i);
  }
  /***********************************************************************/
  public static boolean loadCheckBox(HttpServletRequest req, String key, boolean d)
  {
    return loadCheckBox(req.getParameter(key), d);
  }
  /***********************************************************************/
  public static Cookie loadCookie(HttpServletRequest req, String cookieName)
  {
    return ObjectUtils.getForMethod(req.getCookies(), cookieName, "getName");
  }
  /***********************************************************************/
  public static String loadCookieValue(HttpServletRequest req, String cookieName)
  {
    Cookie cookie = ObjectUtils.getForMethod(req.getCookies(), cookieName, "getName");
    return cookie == null ? null : cookie.getValue();
  }
  /***********************************************************************/
  public static void setCookie(HttpServletResponse response, String name, String value, int maxAge)
  {
    Cookie cookie = new Cookie(name, value);
    cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
  }
  /***********************************************************************/
  public static java.util.Calendar loadDate(HttpServletRequest req, String month, String day, String year)
  {
    int m = load(req.getParameter(month), 0);
    int d = load(req.getParameter(day), 0);
    int y = load(req.getParameter(year), 0);
    java.util.Calendar date = (y * d * m == 0) ? null : new GregorianCalendar(y, m - 1, d, 0, 1);
    return date;
  }
  /***********************************************************************/
  public static Timestamp loadDate(HttpServletRequest req, String key, java.util.Date defaultValue)
  {
    try
    {
      String text = loadNullableString(req, key);
      defaultValue = new java.text.SimpleDateFormat("MM/dd/yyyy").parse(text);
    }
    catch (Exception e)
    {
    }
    return DateUtils.asTimestamp(defaultValue);
  }
  /***********************************************************************/
  public static boolean load(HttpServletRequest req, String key, boolean d)
  {
    return load(req.getParameter(key), d);
  }
  /***********************************************************************/
  public static boolean load(String i, boolean d)
  {
    return NumberUtils.load(i, d);
  }
  /***********************************************************************/
  public static String load(HttpServletRequest req, String key, String d)
  {
    String value = req.getParameter(key);
    return StringUtils.isNonZero(value) ? value.trim() : d;
  }
  /***********************************************************************/
  public static String loadUpperCaseString(String i)
  {
    return com.spun.util.StringUtils.isNonZero(i) ? i.trim().toUpperCase() : null;
  }
  /***********************************************************************/
  public static String loadUpperCaseString(HttpServletRequest req, String key)
  {
    return loadUpperCaseString(req.getParameter(key));
  }
  /***********************************************************************/
  public static String loadNullableString(HttpServletRequest req, String key)
  {
    if (key == null) { return null; }
    String value = req.getParameter(key);
    return StringUtils.isNonZero(value) ? value.trim() : null;
  }
  /***********************************************************************/
  public static String loadNullableString(String value)
  {
    return StringUtils.loadNullableString(value);
  }
  /***********************************************************************/
  protected String getLogFile()
  {
    return null;
  }
  /***********************************************************************/
  protected ParseCall getParser()
  {
    return VelocityParser.FileParseCall.INSTANCE;
  }
  /***********************************************************************/
  protected String getErrorTemplate()
  {
    return null;
  }
  /***********************************************************************/
  public String processError(Throwable t, HttpServletRequest req)
  {
    return processError(t, req, new ErrorToString());
  }
  public String processError(Throwable t, HttpServletRequest req, SecondaryErrorProcessor secondardErrorProcessor)
  {
    try
    {
      error = new TemplateError(t, this);
      String servletInfo = extractServletInformation(req);
      MySystem.warning(servletInfo, t instanceof ServletParameterException ? null : t, ServletLogWriterFactory
          .getWriter(this));
      //      postErrorToLogServer(t, servletInfo);
      return getParser().parse(getErrorTemplate(), error);
    }
    catch (Throwable t2)
    {
      return secondardErrorProcessor.processError(error, t2);
    }
  }
  /***********************************************************************/
  private void postErrorToLogServer(Throwable t, String servletInfo)
  {
    HttpClient client = new HttpClient();
    PostMethod post = new PostMethod("http://mgwap.com:9090/Log/Insert");
    post.addParameter(new NameValuePair("level", "Severe"));
    post.addParameter(new NameValuePair("message", t.getMessage()));
    post.addParameter(new NameValuePair("process", servletInfo));
    StringWriter sw = new StringWriter();
    t.printStackTrace(new PrintWriter(sw));
    post.addParameter(new NameValuePair("stacktrace", sw.toString()));
    try
    {
      client.executeMethod(post);
    }
    catch (HttpException e)
    {
      throw new Error("Unable to contact logging server: " + e.getMessage(), e);
    }
    catch (IOException e)
    {
      throw new Error("Unable to contact logging server: " + e.getMessage(), e);
    }
    finally
    {
      post.releaseConnection();
    }
  }
  /***********************************************************************/
  private String extractServletInformation(HttpServletRequest req)
  {
    if (req == null) { return null; }
    String browser = req.getHeader("user-agent");
    ArrayList<Property> parameters = getParameters(req);
    String servletName = this.getClass().getName();
    String info = "Servlet called from " + browser + "\n" + "[Servlet,Parameters] :  ["
        + servletName.substring(servletName.lastIndexOf(".") + 1) + ", " + parameters + "]";
    return info;
  }
  /***********************************************************************/
  public static ArrayList<Property> getHeaders(HttpServletRequest req)
  {
    Enumeration e = req.getHeaderNames();
    ArrayList<Property> parameters = new ArrayList<Property>();
    while (e.hasMoreElements())
    {
      String s = (String) e.nextElement();
      parameters.add(new Property(s, req.getHeader(s)));
    }
    return parameters;
  }
  /***********************************************************************/
  public static void setContentTypeAsZip(HttpServletResponse res, String fileName)
  {
    res.setContentType("application/zip");
    res.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
    res.setLocale(Locale.US);
  }
  /***********************************************************************/
  public static void setContentTypeAsExcel(HttpServletResponse res, String fileName)
  {
    res.setContentType("application/octet-stream");
    res.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
    res.setLocale(Locale.US);
  }
  /***********************************************************************/
  public static void setContentTypeAsImage(HttpServletResponse res)
  {
    res.setContentType("image/gif");
    res.setLocale(Locale.US);
  }
  /***********************************************************************/
  public static ArrayList<Property> getParameters(HttpServletRequest req)
  {
    Enumeration e = req.getParameterNames();
    ArrayList<Property> parameters = new ArrayList<Property>();
    while (e.hasMoreElements())
    {
      String s = (String) e.nextElement();
      parameters.add(new Property(s, req.getParameter(s)));
    }
    return parameters;
  }
  /***********************************************************************/
  public static Object getSessionData(HttpServletRequest req, String sessionKey)
  {
    Object o = req.getSession().getAttribute(sessionKey);
    if (o == null) { throw new ExpiredSessionError(); }
    return o;
  }
  /***********************************************************************/
  /***********************************************************************/
  public static class Property
  {
    private String value;
    private String name;
    /***********************************************************************/
    public Property(String name, String value)
    {
      this.value = value;
      this.name = name;
    }
    @Override
    public String toString()
    {
      return "[" + name + " = '" + value + "']";
    }
  }
  public static String setContentTypeAsXml(HttpServletResponse res, String xml) throws IOException
  {
    res.setContentType("text/xml");
    ServletOutputStream out = res.getOutputStream();
    out.println(xml);
    out.close();
    return null;
  }
}