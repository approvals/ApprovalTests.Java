package com.spun.util.io;

import com.spun.util.ObjectUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * A static class of convenience functions for Files
 **/
public class NetUtils
{
  public static String loadWebPage(String url) {
    return loadWebPage(url, null);
  }
  public static String loadWebPage(String url, String parameters)
  {
    HttpURLConnection connection = null;
    try
    {
      if (parameters != null && !parameters.isEmpty())
      {
        url += "?" + parameters;
      }
      URL urlObj = URI.create(url).toURL();
      connection = (HttpURLConnection) urlObj.openConnection();
      connection.setRequestMethod("GET");
      connection.connect();
      int responseCode = connection.getResponseCode();
      if (responseCode != HttpURLConnection.HTTP_OK)
      { throw new RuntimeException("Failed to load web page, response code: " + responseCode); }
      InputStream inputStream = connection.getInputStream();
      return FileUtils.readStream(inputStream);
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
    finally
    {
      if (connection != null)
      {
        connection.disconnect();
      }
    }
  }
  /**
   * @deprecated Use {@link #loadWebPage(String)} instead.
   */
  @Deprecated
  public static String readWebpage(String query)
  {
    return loadWebPage(query, null);
  }
}
