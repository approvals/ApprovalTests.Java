package org.approvaltests.approvers;

import org.approvaltests.core.ApprovalFailureReporter;

public interface ApprovalApprover
{
  boolean approve();
  public void cleanUpAfterSuccess(ApprovalFailureReporter reporter);
  void fail();
  void reportFailure(ApprovalFailureReporter reporter);
}
