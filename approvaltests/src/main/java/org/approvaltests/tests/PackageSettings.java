package org.approvaltests.tests;

import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.reporters.DiffReporter;

public class PackageSettings
{
  public static class ApprovalTestsPackageLevelReporter extends DiffReporter
  {
  }
  public static ApprovalFailureReporter UseReporter = new ApprovalTestsPackageLevelReporter();
}
