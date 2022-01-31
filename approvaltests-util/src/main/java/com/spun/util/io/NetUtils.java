package com.spun.util.io;

import com.spun.util.ObjectUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.InputStream;
import java.net.URL;

/**
 * A static class of convenience functions for Files
 **/
public class NetUtils
{
  public static String loadWebPage(String url, String parameters)
  {
    try
    {
      HttpClient client = new HttpClient();
      GetMethod method = new GetMethod(url);
      if (parameters != null)
      {
        method.setQueryString(parameters);
      }
      client.executeMethod(method);
      String html = method.getResponseBodyAsString();
      return html;
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  public static String readWebpage(String query)
  {
    try
    {
      URL url = new URL(query);
      InputStream inputStream = url.openStream();
      return FileUtils.readStream(inputStream);
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
}
