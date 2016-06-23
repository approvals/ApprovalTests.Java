package org.approvaltests.namer.tests;

import java.io.File;

import junit.framework.TestCase;

import org.approvaltests.namer.StackTraceNamer;

public class JUnitStackTraceNamerTest extends TestCase
{
  public void testGetApprovalName() throws Exception
  {
    StackTraceNamer name = new StackTraceNamer();
    assertEquals("JUnitStackTraceNamerTest.testGetApprovalName", name.getApprovalName());
  }
  public void testGetSourceFilePath() throws Exception
  {
    StackTraceNamer name = new StackTraceNamer();
    File file = new File(name.getSourceFilePath() + "JUnitStackTraceNamerTest.java");
    assertTrue(file.exists());
  }
  public void testEmbeddedStackName()
  {
    StackTraceNamer namer = createJUnitStackNamer();
    assertEquals("JUnitStackTraceNamerTest.testEmbeddedStackName", namer.getApprovalName());
  }
  private StackTraceNamer createJUnitStackNamer()
  {
    return new StackTraceNamer();
  }
}
