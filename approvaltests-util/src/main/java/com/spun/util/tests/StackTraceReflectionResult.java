package com.spun.util.tests;

import java.io.File;

public class StackTraceReflectionResult
{
  private final File   sourceFile;
  private final String className;
  private final String methodName;
  private String       fullClassName;
  public StackTraceReflectionResult(File sourceFile, String className, String fullClassName, String methodName)
  {
    this.sourceFile = sourceFile;
    this.className = className;
    this.fullClassName = fullClassName;
    this.methodName = methodName;
  }
  public File getSourceFile()
  {
    return sourceFile;
  }
  public String getClassName()
  {
    return className;
  }
  public String getMethodName()
  {
    return methodName;
  }
  public String getFullClassName()
  {
    return fullClassName;
  }
}
