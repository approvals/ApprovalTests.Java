package org.approvaltests.namer.tests;

import java.io.File;

import org.approvaltests.namer.JUnitStackTraceNamer;
import org.junit.Assert;
import org.junit.Test;

public class JUnit4StackTraceNamerTest
{
  @Test
  public void testGetApprovalName() throws Exception
  {
    JUnitStackTraceNamer name = new JUnitStackTraceNamer();
    Assert.assertEquals("JUnit4StackTraceNamerTest.testGetApprovalName", name.getApprovalName());
  }
  @Test
  public void testGetSourceFilePath() throws Exception
  {
    JUnitStackTraceNamer name = new JUnitStackTraceNamer();
    File file = new File(name.getApprovalFileBasePath() + "JUnitStackTraceNamerTest.java");
    Assert.assertTrue(file.exists());
  }
}
