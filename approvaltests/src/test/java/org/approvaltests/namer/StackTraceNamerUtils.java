package org.approvaltests.namer;

import org.approvaltests.Approvals;
import org.junit.Assert;

import java.io.File;

public class StackTraceNamerUtils
{
  public static void assertNamerForFramework(String className, String methodName)
  {
    StackTraceNamerUtils.assertApprovalName(className, methodName);
    StackTraceNamerUtils.assertSourceFilePath(className);
  }
  public static void assertApprovalName(String className, String methodName)
  {
    StackTraceNamer name = new StackTraceNamer();
    Assert.assertEquals(className + "." + methodName, name.getApprovalName());
  }
  public static void assertSourceFilePath(String className)
  {
    StackTraceNamer name = new StackTraceNamer();
    File file = new File(name.getSourceFilePath() + className + ".java");
    Assert.assertTrue(file.exists());
  }
  public static void assertParameterizedTest(String className, String methodName, String input)
  {
    try (NamedEnvironment en = NamerFactory.asMachineSpecificTest(input))
    {
      ApprovalNamer name = Approvals.createApprovalNamer();
      org.testng.Assert.assertEquals(className + "." + methodName + "." + input, name.getApprovalName());
    }
  }
}
