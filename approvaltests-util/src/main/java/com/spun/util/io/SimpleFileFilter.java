package com.spun.util.io;

import java.io.File;

public class SimpleFileFilter implements java.io.FileFilter
{
  public SimpleFileFilter()
  {
  }

  public boolean accept(File pathname)
  {
    String name = pathname.getName().toLowerCase();
    boolean accept = false;
    if (name.equals(".") || name.equals(".."))
    {
      accept = false;
    }
    else if (pathname.isDirectory())
    {
      accept = false;
    }
    else
    {
      accept = true;
    }
    return accept;
  }
}