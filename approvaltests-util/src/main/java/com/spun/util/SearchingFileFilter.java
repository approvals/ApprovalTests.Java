package com.spun.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchingFileFilter implements FilenameFilter
{
  private final List<String> matches;
  public SearchingFileFilter(List<String> matches)
  {
    this.matches = matches;
  }
  public SearchingFileFilter(String... matches)
  {
    ArrayList<String> m = new ArrayList<String>();
    m.addAll(Arrays.asList(matches));
    this.matches = m;
  }
  public boolean accept(File dir, String name)
  {
    boolean directory = new File(dir, name).isDirectory();
    return accept(name, directory);
  }
  public boolean accept(String name, boolean directory)
  {
    if (name.startsWith("."))
    { return false; }
    if (matches.get(0).equals("*"))
    { return directory || name.equals(matches.get(1)); }
    return name.equals(matches.get(0));
  }
  public List<String> getSubset(String file2)
  {
    if (matches.get(0).equals("*"))
    {
      if (matches.get(1).equals(file2))
      {
        return matches.subList(2, matches.size());
      }
      else
      {
        return matches;
      }
    }
    return matches.subList(1, matches.size());
  }
}
