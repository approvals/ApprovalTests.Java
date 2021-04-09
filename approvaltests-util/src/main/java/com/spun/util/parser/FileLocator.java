package com.spun.util.parser;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

public class FileLocator implements ResourceLocator
{
  private HashMap<String, String> paths          = new HashMap<String, String>();
  private boolean                 failOnNotFound = true;
  private String[]                basePaths;
  public FileLocator(String[] basePaths)
  {
    this(basePaths, true);
  }
  public FileLocator(String[] basePaths, boolean failOnNotFound)
  {
    this.basePaths = basePaths;
    this.failOnNotFound = failOnNotFound;
  }
  public String getLocation(String fileName)
  {
    String absolutePath = paths.get(fileName);
    if (absolutePath == null)
    {
      for (int i = 0; i < basePaths.length; i++)
      {
        File file = new File(basePaths[i] + File.separator + fileName);
        if (file.exists())
        {
          absolutePath = registerFile(fileName, file);
          break;
        }
      }
    }
    if (absolutePath == null)
    {
      if (failOnNotFound)
      {
        throw new NullPointerException(" The file '" + fileName + "' could not be found in "
            + Arrays.asList(basePaths).toString() + " with base dir = " + new File(".").getAbsolutePath());
      }
      else
      {
        File file = new File(basePaths[0] + File.separator + fileName);
        absolutePath = registerFile(fileName, file);
      }
    }
    return absolutePath;
  }
  private String registerFile(String fileName, File file)
  {
    String absolutePath = file.getAbsolutePath();
    paths.put(fileName, absolutePath);
    return absolutePath;
  }
}
