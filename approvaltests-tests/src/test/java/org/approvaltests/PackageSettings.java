package org.approvaltests;

import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.MultiReporter;

public class PackageSettings
{
  public static class ApprovalTestsPackageLevelReporter extends DiffReporter
  {
  }
  public static ApprovalFailureReporter UseReporter                     = new ApprovalTestsPackageLevelReporter();
  public static ApprovalFailureReporter FrontloadedReporter             = new MultiReporter();
  public static boolean                 AllowMultipleVerifyCallsPerTest = true;
}
