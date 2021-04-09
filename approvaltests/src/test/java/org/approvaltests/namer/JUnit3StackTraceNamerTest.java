package org.approvaltests.namer;

import junit.framework.TestCase;

public class JUnit3StackTraceNamerTest extends TestCase
{
  public void testGetApprovalName()
  {
    NamerFactory.getAndClearAdditionalInformation();
    StackTraceNamerUtils.assertNamerForFramework("JUnit3StackTraceNamerTest", "testGetApprovalName");
  }
}
