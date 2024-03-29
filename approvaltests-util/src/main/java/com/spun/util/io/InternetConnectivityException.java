package com.spun.util.io;

import com.spun.util.logger.SimpleLogger;
import org.apache.commons.net.echo.EchoTCPClient;

import java.net.InetAddress;

/**
 * A static class of convenience functions for Files
 **/
public class InternetConnectivityException extends Error
{
  private static final long serialVersionUID = 1L;
  private String            htmlText;
  private Throwable         cause;
  public void setCause(Throwable t)
  {
    this.cause = t;
  }
  public InternetConnectivityException(String string)
  {
    super(string);
  }
  public void setHTMLText(String htmlText)
  {
    this.htmlText = htmlText;
  }
  public String getHTMLText()
  {
    return htmlText;
  }
  public static InternetConnectivityException testInternetConnectivity()
  {
    String[] sites = {"www.google.com", "www.yahoo.com", "www.msn.com"};
    for (int i = 0; i < sites.length; i++)
    {
      if (pingSite(sites[i]))
      { return null; }
    }
    return new InternetConnectivityException("There is no internet connection.");
  }
  public String toString()
  {
    return super.toString() + "\n" + (cause == null ? "" : "Causation:  " + cause.toString());
  }
  private static boolean pingSite(String site)
  {
    try
    {
      EchoTCPClient client = new EchoTCPClient();
      client.setDefaultTimeout(1000);
      client.connect(InetAddress.getByName(site), 80);
      client.disconnect();
      return true;
    }
    catch (Exception e)
    {
      SimpleLogger.warning(site, e);
      return false;
    }
  }
}
