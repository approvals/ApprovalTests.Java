package org.approvaltests.reporters;

import com.spun.util.ClassUtils;
import com.spun.util.QuietAutoCloseable;
import com.spun.util.io.FileUtils;
import com.spun.util.logger.SimpleLogger;
import org.approvaltests.Approvals;
import org.approvaltests.core.ApprovalFailureReporter;
import org.junit.jupiter.api.Test;

public class JUnitReporterTest
{
  @Test
  void testJUnit3()
  {
    verifyReporter(new Junit3Reporter());
  }
  @Test
  void testJUnit4()
  {
    verifyReporter(new Junit4Reporter());
  }
  @Test
  void testJUnit5()
  {
    verifyReporter(new Junit5Reporter());
  }
  private void verifyReporter(ApprovalFailureReporter reporter)
  {
    try (QuietAutoCloseable l = SimpleLogger.quiet())
    {
      String a = FileUtils.getResolvedPath(ClassUtils.getAdjacentFile(this.getClass(), "a.txt"));
      String b = FileUtils.getResolvedPath(ClassUtils.getAdjacentFile(this.getClass(), "b.txt"));
      Approvals.verifyException(() -> {
        reporter.report(b, a);
      });
    }
  }
}
