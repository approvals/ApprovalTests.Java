package org.samples;

import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.reporters.DiffReporter;

/**
  * Here you can set the default reporter used for everything 
  * starting with org.samples.* including sub-packages
  **/
public class PackageSettings
{
  public ApprovalFailureReporter UseReporter = DiffReporter.INSTANCE;
}
