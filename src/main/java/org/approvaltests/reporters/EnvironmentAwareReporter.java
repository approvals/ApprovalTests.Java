package org.approvaltests.reporters;

import org.approvaltests.core.ApprovalFailureReporter;

public interface EnvironmentAwareReporter extends ApprovalFailureReporter
{
  boolean isWorkingInThisEnvironment(String forFile);
}
