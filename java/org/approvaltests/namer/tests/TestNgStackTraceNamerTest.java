package org.approvaltests.namer.tests;

import java.io.File;

import org.approvaltests.namer.StackTraceNamer;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestNgStackTraceNamerTest
{
  @Test
  public void testGetApprovalName() throws Exception
  {
    StackTraceNamer name = new StackTraceNamer();
    Assert.assertEquals("TestNgStackTraceNamerTest.testGetApprovalName", name.getApprovalName());
  }
  @Test
  public void testGetSourceFilePath() throws Exception
  {
    StackTraceNamer name = new StackTraceNamer();
    File file = new File(name.getSourceFilePath() + "TestNgStackTraceNamerTest.java");
    Assert.assertTrue(file.exists());
  }
}
