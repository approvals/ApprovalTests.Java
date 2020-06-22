package org.approvaltests.namer.tests;

import org.junit.jupiter.api.Test;

public class JUnit5StackTraceNamerTest
{
  @Test
  public void testGetApprovalName()
  {
    StackTraceNamerUtils.assertNamerForFramework("JUnit5StackTraceNamerTest", "testGetApprovalName");
  }
}
