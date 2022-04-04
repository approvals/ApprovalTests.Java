package com.spun.util;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

public class SystemUtils
{
  /**
   * @deprecated Use {@link #isWindowsEnvironment()} instead.
   * For example inline your usage of this method.
   */
  @Deprecated
  public static boolean isWindowsEnviroment()
  {
    return isWindowsEnvironment();
  }
  public static boolean isWindowsEnvironment()
  {
    return "\\".equals(File.separator);
  }
  public static String convertFileForCommandLine(String fileName)
  {
    return convertFileForCommandLine(fileName, SystemUtils.isWindowsEnviroment());
  }
  public static String getComputerName()
  {
    Map<String, String> env = System.getenv();
    if (env.containsKey("COMPUTERNAME"))
    {
      return env.get("COMPUTERNAME");
    }
    else if (env.containsKey("HOSTNAME"))
    {
      return env.get("HOSTNAME");
    }
    else
    {
      String hostname = "Unknown Computer";
      try
      {
        InetAddress addr;
        addr = InetAddress.getLocalHost();
        hostname = addr.getHostName();
      }
      catch (UnknownHostException ex)
      {
        //System.out.println("Hostname can not be resolved");
      }
      return hostname;
    }
  }
  public static String convertFileForCommandLine(String fileName, boolean windowsOs)
  {
    if (!fileName.contains(" "))
    {
      return fileName;
    }
    else if (windowsOs)
    {
      return String.format("\"%s\"", fileName);
    }
    else
    {
      return fileName.replace(" ", "\\ ");
    }
  }
  /**
   * @deprecated Use {@link #isMacEnvironment()} instead.
   * For example inline your usage of this method.
   */
  @Deprecated
  public static boolean isMacEnviroment()
  {
    return isMacEnvironment();
  }
  public static boolean isMacEnvironment()
  {
    String osName = System.getProperty("os.name").toLowerCase();
    boolean isMacOs = osName.startsWith("mac os x");
    return !isWindowsEnviroment() && isMacOs;
  }
}
