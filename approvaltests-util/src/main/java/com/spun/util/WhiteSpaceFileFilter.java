package com.spun.util;

import java.io.File;

public class WhiteSpaceFileFilter implements java.io.FileFilter
{
  @Override
  public boolean accept(File pathname)
  {
    if (pathname.getName().equals("."))
    {
      return false;
    }
    else if (pathname.getName().equalsIgnoreCase("email"))
    {
      return false;
    }
    else if (pathname.isDirectory() || (pathname.getName().contains(".htm"))
        || (pathname.getName().contains(".txt")))
    {
      return true;
    }
    else
    {
      return false;
    }
  }
}
