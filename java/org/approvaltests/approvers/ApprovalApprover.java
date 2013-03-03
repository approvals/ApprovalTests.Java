package org.approvaltests.approvers;

import org.approvaltests.core.ApprovalFailureReporter;

public interface ApprovalApprover
{
  boolean approve() throws Exception;
  public void cleanUpAfterSuccess(ApprovalFailureReporter reporter) throws Exception;
  void fail();
  void reportFailure(ApprovalFailureReporter reporter) throws Exception;
}
