package com.spun.util;

import java.io.File;

public class SystemUtils
{
  public static boolean isWindowsEnviroment()
  {
    return "\\".equals(File.separator);
  }
  public static String convertFileForCommandLine(String fileName)
  {
    return convertFileForCommandLine(fileName, SystemUtils.isWindowsEnviroment());
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
}
