package org.approvaltests.namer.tests;

import org.junit.Test;

import junit.framework.TestCase;

public class JUnit3StackTraceNamerTest extends TestCase
{
  @Test
  public void testGetApprovalName()
  {
    StackTraceNamerUtils.assertNamerForFramework("JUnit3StackTraceNamerTest", "testGetApprovalName");
  }
}
