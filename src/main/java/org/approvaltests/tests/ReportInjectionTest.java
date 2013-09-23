package org.approvaltests.tests;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.approvaltests.core.ApprovalFailureOverrider;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.reporters.QuietReporter;
import org.approvaltests.reporters.UseReporter;

@UseReporter(QuietReporter.class)
public class ReportInjectionTest extends TestCase
{
  public static class MyReporter implements ApprovalFailureReporter, ApprovalFailureOverrider
  {
    @Override
    public void report(String received, String approved) throws Exception
    {
    }
    @Override
    public boolean askToChangeReceivedToApproved(String received, String approved) throws Exception
    {
      called = getClass();
      return true;
    }
  }
  private static Class called = null;
  @UseReporter(MyReporter.class)
  public void testOverrideReporterByParameter() throws Exception
  {
    Approvals.verify("some text");
    assertEquals(MyReporter.class, called);
  }
}
