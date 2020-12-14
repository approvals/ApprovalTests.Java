package org.approvaltests.reporters;

import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.VerifyResult;

public interface ReporterWithApprovalPower extends ApprovalFailureReporter
{
  public VerifyResult approveWhenReported();
}
