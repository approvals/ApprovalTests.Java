package org.approvaltests.reporters;

import com.spun.util.ThreadUtils;
import com.spun.util.io.FileUtils;
import org.approvaltests.ReporterFactory;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.windows.ReportWithTortoiseTextDiffWindows;
import org.junit.jupiter.api.Test;
import org.lambda.query.Queryable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UseReporterTest
{
  @UseReporter(ReportWithTortoiseTextDiffWindows.class)
  @Test
  public void testUseReporter()
  {
    assertEquals(
        ReportWithTortoiseTextDiffWindows.class,
        ReporterFactory.getFromAnnotation(ThreadUtils.getStackTrace()).getClass());
  }

  @UseReporter({ReportWithTortoiseTextDiffWindows.class, ClipboardReporter.class})
  @Test
  public void testMultipleUseReporter()
  {
    assertEquals(MultiReporter.class, ReporterFactory.getFromAnnotation(ThreadUtils.getStackTrace()).getClass());
  }

  @UseReporter(TestReporter.class)
  @Test
  void testUseReporterIsCalledWhenMethodIsNotPublic()
  {
    FirstWorkingReporter reporter = (FirstWorkingReporter) new Options().getReporter();
    assertTrue(Queryable.of(reporter.getReporters()).any(r -> r instanceof TestReporter));
  }
  public static class TestReporter implements ApprovalFailureReporter
  {
    static String last;
    public static String getLast()
    {
      return last;
    }

    @Override
    public boolean report(String received, String approved)
    {
      last = FileUtils.readFile(received);
      return true;
    }
  }
}
