package org.approvaltests.core;

public interface ApprovalFailureReporter
{
  public void report(String received, String approved) throws Exception;
}
