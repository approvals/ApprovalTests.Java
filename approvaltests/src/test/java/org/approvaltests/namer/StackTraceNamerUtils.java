package org.approvaltests.namer;

import org.approvaltests.Approvals;
import org.junit.Assert;

import java.io.File;

public class StackTraceNamerUtils
{
  public static void assertNamerForFramework(String className, String methodName)
  {
    final ApprovalNamer namer = new StackTraceNamer();
    assertNamerForFramework(className, methodName, namer);
  }

  public static void assertNamerForFramework(String className, String methodName, ApprovalNamer namer)
  {
    StackTraceNamerUtils.assertApprovalName(className, methodName, namer);
    StackTraceNamerUtils.assertSourceFilePath(className, namer);
  }

  public static void assertApprovalName(String className, String methodName, ApprovalNamer namer)
  {
    Assert.assertEquals(className + "." + methodName, namer.getApprovalName());
  }

  public static void assertSourceFilePath(String className, ApprovalNamer name)
  {
    File file = new File(name.getSourceFilePath() + className + ".java");
    Assert.assertTrue(file.exists());
  }

  public static void assertParameterizedTest(String className, String methodName, String input)
  {
    ApprovalNamer name = Approvals.NAMES.withParameters(input).forFile().getNamer();
    org.testng.Assert.assertEquals(className + "." + methodName + "." + input, name.getApprovalName());
  }
}
