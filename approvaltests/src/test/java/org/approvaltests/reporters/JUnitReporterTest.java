package org.approvaltests.reporters;

import com.spun.util.ClassUtils;
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
    String a = ClassUtils.getAdjacentFile(this.getClass(), "a.txt").getAbsolutePath();
    String b = ClassUtils.getAdjacentFile(this.getClass(), "b.txt").getAbsolutePath();
    Approvals.verifyException(() -> {
      reporter.report(b, a);
    });
  }
}
