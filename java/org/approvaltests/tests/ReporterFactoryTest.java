package org.approvaltests.tests;

import junit.framework.TestCase;

import org.approvaltests.ReporterFactory;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.QuietReporter;

public class ReporterFactoryTest extends TestCase
{
  public void testReporters() throws Exception
  {
    assertEquals(DiffReporter.class, getClassFor("txt"));
    assertEquals(DiffReporter.class, getClassFor("html"));
    assertEquals(QuietReporter.class, getClassFor("other"));
  }
  private Class getClassFor(String type)
  {
    return ReporterFactory.get(type).getClass();
  }
}
