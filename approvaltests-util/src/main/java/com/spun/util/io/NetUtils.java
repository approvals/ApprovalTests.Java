package com.spun.util.io;

import com.spun.util.ObjectUtils;
import com.spun.util.logger.Markers;
import com.spun.util.logger.SimpleLogger;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

/**
 * A static class of convenience functions for Files
 **/
public class NetUtils
{
  public static String loadWebPage(String url)
  {
    return loadWebPage(url, null);
  }
  public static String loadWebPage(String url, String parameters)
  {
    return loadWebPage(url, parameters, null);
  }
  public static String loadWebPage(String url, String parameters, Duration timeout)
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
      if (timeout != null) {
        connection.setConnectTimeout((int) timeout.toMillis());
      }
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
