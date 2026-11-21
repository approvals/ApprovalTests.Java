package org.approvaltests;

import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.FirstWorkingReporter;
import org.approvaltests.reporters.QuietReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;
import org.lambda.query.Queryable;

import static org.junit.jupiter.api.Assertions.assertTrue;

@UseReporter(QuietReporter.class)
public class ReportInjectionTest
{
  public static class MyReporter implements ApprovalFailureReporter
  {
    @Override
    public boolean report(String received, String approved)
    {
      return true;
    }
  }
  @UseReporter(MyReporter.class)
  @Test
  public void testOverrideReporterByParameter()
  {
    FirstWorkingReporter reporter = (FirstWorkingReporter) new Options().getReporter();
    assertTrue(Queryable.of(reporter.getReporters()).any(r -> r instanceof MyReporter));
  }
}
