package org.approvaltests.tests;

import org.approvaltests.Approvals;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.reporters.QuietReporter;
import org.approvaltests.reporters.UseReporter;

import junit.framework.TestCase;

@UseReporter(QuietReporter.class)
public class ReportInjectionTest extends TestCase
{
  public static class MyReporter implements ApprovalFailureReporter
  {
    @Override
    public void report(String received, String approved)
    {
      called = getClass();
    }
  }
  private static Class<? extends MyReporter> called = null;
  @UseReporter(MyReporter.class)
  public void testOverrideReporterByParameter() throws Exception
  {
    try
    {
      Approvals.verify("some text");
    }
    catch (Throwable e)
    {
      assertEquals(MyReporter.class, called);
    }
  }
}
