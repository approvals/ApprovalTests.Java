package org.approvaltests.reporters;

import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.VerifyResult;

public class FirstWorkingReporter implements ReporterWithApprovalPower
{
  private final ApprovalFailureReporter[] reporters;
  private VerifyResult approvalOutcome = VerifyResult.FAILURE;

  public FirstWorkingReporter(ApprovalFailureReporter... reporters)
  {
    this.reporters = reporters;
  }
  public static FirstWorkingReporter combine(ApprovalFailureReporter front, ApprovalFailureReporter last)
  {
    return new FirstWorkingReporter(front, last);
  }
  @Override
  public boolean report(String received, String approved)
  {
    for (ApprovalFailureReporter reporter : reporters)
    {
      if (reporter.report(received, approved))
      {
        checkApprovalPower(reporter);
        return true;
      }
    }
    return false;
  }

  private void checkApprovalPower(ApprovalFailureReporter reporter) {
    if (reporter instanceof ReporterWithApprovalPower) {
      approvalOutcome = ((ReporterWithApprovalPower) reporter).approveWhenReported();
    }
  }

  public ApprovalFailureReporter[] getReporters()
  {
    return reporters;
  }
  @Override
  public String toString()
  {
    return getClass().getName();
  }

  @Override
  public VerifyResult approveWhenReported() {
    return approvalOutcome;
  }
}
