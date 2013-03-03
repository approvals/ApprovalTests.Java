package com.spun.util;

import java.io.File;

public class SystemUtils
{
  public static boolean isWindowsEnviroment()
  {
    return "\\".equals(File.separator);
  }
}
