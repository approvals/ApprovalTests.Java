package org.approvaltests;

import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.reporters.QuietReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UseReporter(QuietReporter.class)
public class ReportInjectionTest
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
  @Test
  public void testOverrideReporterByParameter()
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
