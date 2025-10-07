package com.spun.util.io.filefilters;

import java.io.File;
import java.io.FileFilter;

public class JavaClassFileFilter implements FileFilter
{
  private final String classSuffix;
  private String       startsWith;
  public JavaClassFileFilter(String startsWith, String classSuffix)
  {
    this.classSuffix = classSuffix;
    this.startsWith = startsWith.replace('.', File.separatorChar);
  }

  public boolean accept(File pathname)
  {
    String name = pathname.getName();
    boolean fileIsTest = name.endsWith(classSuffix + ".class") && pathname.toString().contains(startsWith);
    return fileIsTest;
  }
}