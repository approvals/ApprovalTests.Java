package com.spun.util;

import java.io.File;

public class WhiteSpaceFileFilter implements java.io.FileFilter
{
  @Override
  public boolean accept(File pathname)
  {
    if (".".equals(pathname.getName()))
    {
      return false;
    }
    else if ("email".equalsIgnoreCase(pathname.getName()))
    {
      return false;
    }
    else
    {
      return pathname.isDirectory() || (pathname.getName().contains(".htm"))
          || (pathname.getName().contains(".txt"));
    }
  }
}
