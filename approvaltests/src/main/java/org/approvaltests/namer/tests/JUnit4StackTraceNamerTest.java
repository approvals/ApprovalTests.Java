package org.approvaltests.namer.tests;

import java.io.File;

import org.approvaltests.namer.StackTraceNamer;
import org.junit.Assert;
import org.junit.Test;

public class JUnit4StackTraceNamerTest
{
  @Test
  public void testGetApprovalName() throws Exception
  {
    StackTraceNamer name = new StackTraceNamer();
    Assert.assertEquals("JUnit4StackTraceNamerTest.testGetApprovalName", name.getApprovalName());
  }
  @Test
  public void testGetSourceFilePath() throws Exception
  {
    StackTraceNamer name = new StackTraceNamer();
    File file = new File(name.getSourceFilePath() + "JUnitStackTraceNamerTest.java");
    Assert.assertTrue(file.exists());
  }
}
