package com.spun.util.parser;

import java.util.HashMap;

public class JarFileLocator implements ResourceLocator
{
  private HashMap<String, String> paths = new HashMap<String, String>();
  private String                  basePath;
  public JarFileLocator(String basePath)
  {
    this.basePath = basePath;
  }
  public String getLocation(String fileName)
  {
    String absolutePath = paths.get(fileName);
    String temp = basePath + '/' + fileName;
    if (absolutePath == null)
    {
      ClassLoader cl = JarFileLocator.class.getClassLoader();
      if (cl.getResource(temp) != null)
      {
        absolutePath = temp;
        paths.put(fileName, absolutePath);
      }
    }
    if (absolutePath == null)
    { throw new NullPointerException(" The resource '" + fileName + "' could not be found at " + temp); }
    return absolutePath;
  }
}