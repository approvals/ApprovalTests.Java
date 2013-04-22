package org.approvaltests.namer.tests;

import java.io.File;

import junit.framework.TestCase;

import org.approvaltests.namer.JUnitStackTraceNamer;

public class JUnitStackTraceNamerTest extends TestCase
{
  public void testGetApprovalName() throws Exception
  {
    JUnitStackTraceNamer name = new JUnitStackTraceNamer();
    assertEquals("JUnitStackTraceNamerTest.testGetApprovalName", name.getApprovalName());
  }
  public void testGetApprovalBasePath() throws Exception
  {
    JUnitStackTraceNamer name = new JUnitStackTraceNamer();
    File file = new File(name.getApprovalFileBasePath() + "JUnitStackTraceNamerTest.java");
    assertTrue(file.exists());
  }
  public void testEmbeddedStackName()
  {
    JUnitStackTraceNamer namer = createJUnitStackNamer();
    assertEquals("JUnitStackTraceNamerTest.testEmbeddedStackName", namer.getApprovalName());
  }
  private JUnitStackTraceNamer createJUnitStackNamer()
  {
    return new JUnitStackTraceNamer();
  }
}
