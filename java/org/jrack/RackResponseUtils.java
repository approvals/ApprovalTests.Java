package org.jrack;

import java.util.HashMap;
import java.util.Map;

public class RackResponseUtils
{
  public static class ReturnCode
  {
    public static final int OK = 200;
  }
  public static final String CONTENT_TYPE_TEXT_HTML = "text/html";
  public static final String CONTENT_TYPE_IMAGE     = "image/gif";
  public static final String CONTENT_TYPE           = "Content-Type";
  public static RackResponse standardHtml(String html)
  {
    return new RackResponse(RackResponseUtils.ReturnCode.OK, getStandardHtmlHeader(), html);
  }
  public static Map<String, String> getStandardHtmlHeader()
  {
    Map<String, String> header = new HashMap<String, String>();
    header.put(CONTENT_TYPE, CONTENT_TYPE_TEXT_HTML);
    return header;
  }
  public static RackResponse image(char[] data)
  {
    Map<String, String> headers = new HashMap<String, String>();
    headers.put(CONTENT_TYPE, CONTENT_TYPE_IMAGE);
    RackResponse response = new RackResponse(ReturnCode.OK, headers, data);
    return response;
  }
}
