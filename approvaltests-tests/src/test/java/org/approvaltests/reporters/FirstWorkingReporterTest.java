package org.approvaltests.reporters;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.core.VerifyResult;
import org.junit.jupiter.api.Test;

class FirstWorkingReporterTest
{
  @Test
  void testReporterWithApprovalPower()
  {
    var t = new ReporterWithApprovalPower()
    {
      @Override
      public boolean report(String received, String approved)
      {
        return true;
      }

      @Override
      public VerifyResult approveWhenReported()
      {
        return VerifyResult.SUCCESS;
      }
    };
    Approvals.verify("test", new Options(new FirstWorkingReporter(t)));
  }

  @Test
  void testToString()
  {
    var expected = """
        FirstWorkingReporter(ReporterForTesting(True), ReporterForTesting(False))
        """;
    var reporter = new FirstWorkingReporter(new ReporterForTesting(true), new ReporterForTesting(false));
    Approvals.verify(reporter.toString(), new Options().inline(expected));
  }
}
