package com.spun.util.io.filefilters;

import java.io.File;
import java.io.FilenameFilter;

public class ExtenstionFileFilter implements FilenameFilter
{
  private final String[] extenstions;
  public ExtenstionFileFilter(String... extenstions)
  {
    this.extenstions = extenstions;
  }
  @Override
  public boolean accept(File dir, String name)
  {
    for (String end : extenstions)
    {
      if (name.endsWith(end)) { return true; }
    }
    return false;
  }
}
