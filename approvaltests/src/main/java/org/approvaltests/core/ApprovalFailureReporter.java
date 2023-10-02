package org.approvaltests.core;

public interface ApprovalFailureReporter
{
  public boolean report(String received, String approved);
}
