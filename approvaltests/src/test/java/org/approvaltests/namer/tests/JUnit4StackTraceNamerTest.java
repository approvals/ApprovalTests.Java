package org.approvaltests.namer.tests;

import org.junit.Test;

public class JUnit4StackTraceNamerTest
{
  @Test
  public void testGetApprovalName()
  {
    StackTraceNamerUtils.assertNamerForFramework("JUnit4StackTraceNamerTest", "testGetApprovalName");
  }
}
