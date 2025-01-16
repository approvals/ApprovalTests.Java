package com.spun.util.io;

import com.spun.util.ObjectUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A static class of convenience functions for Files
 **/
public class NetUtils
{
  public static String loadWebPage(String url, String parameters)
  {
    HttpURLConnection connection = null;
    try
    {
      if (parameters != null && !parameters.isEmpty())
      {
        url += "?" + parameters;
      }
      URL urlObj = new URL(url);
      connection = (HttpURLConnection) urlObj.openConnection();
      connection.setRequestMethod("GET");
      connection.connect();

      int responseCode = connection.getResponseCode();
      if (responseCode != HttpURLConnection.HTTP_OK)
      {
        throw new RuntimeException("Failed to load web page, response code: " + responseCode);
      }

      InputStream inputStream = connection.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      StringBuilder html = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null)
      {
        html.append(line);
      }
      reader.close();
      return html.toString();
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

  public static String readWebpage(String query)
  {
    HttpURLConnection connection = null;
    try
    {
      URL url = new URL(query);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.connect();

      int responseCode = connection.getResponseCode();
      if (responseCode != HttpURLConnection.HTTP_OK)
      {
        throw new RuntimeException("Failed to read web page, response code: " + responseCode);
      }

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
}
