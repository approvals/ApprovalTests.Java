package com.spun.util.tests;

import java.io.File;

public class StackTraceReflectionResult
{

  private final File sourceFile;
  private final String className;
  private final String methodName;

  public StackTraceReflectionResult(File sourceFile, String className, String methodName)
  {
    this.sourceFile = sourceFile;
    this.className = className;
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
  
}
