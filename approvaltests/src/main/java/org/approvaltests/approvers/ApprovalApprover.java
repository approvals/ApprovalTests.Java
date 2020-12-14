package org.approvaltests.approvers;

import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.VerifyResult;

public interface ApprovalApprover
{
  VerifyResult approve();
  public void cleanUpAfterSuccess(ApprovalFailureReporter reporter);
  void fail();
  VerifyResult reportFailure(ApprovalFailureReporter reporter);
}
