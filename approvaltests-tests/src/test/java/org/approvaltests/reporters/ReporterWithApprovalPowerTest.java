package org.approvaltests.reporters;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.core.VerifyResult;
import org.junit.jupiter.api.Test;

public class ReporterWithApprovalPowerTest
{
  @Test
  void testReporterCanApprove()
  {
    Approvals.verify("applesauce", new Options().withReporter(new TestCleanUpReporter()));
  }
  private static class TestCleanUpReporter implements ReporterWithApprovalPower
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
  }
}
