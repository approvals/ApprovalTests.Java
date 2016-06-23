package org.approvaltests.namer;

import java.io.File;

import com.spun.util.tests.StackTraceReflectionResult;
import com.spun.util.tests.TestUtils;

public class StackTraceNamer implements ApprovalNamer
{
  private StackTraceReflectionResult info;
  public StackTraceNamer()
  {
    info = TestUtils.getCurrentFileForMethod(new AttributeStackSelector());
  }
  @Override
  public String getApprovalName()
  {
    return String.format("%s.%s", info.getClassName(), info.getMethodName());
  }
  @Override
  public String getSourceFilePath()
  {
    return info.getSourceFile().getAbsolutePath() + File.separator;
  }
}
