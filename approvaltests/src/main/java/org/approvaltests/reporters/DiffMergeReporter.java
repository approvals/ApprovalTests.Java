package org.approvaltests.reporters;

import org.approvaltests.reporters.linux.DiffMergeLinuxReporter;
import org.approvaltests.reporters.macosx.DiffMergeMacOsReporter;

public class DiffMergeReporter extends FirstWorkingReporter
{
  public static final DiffMergeReporter INSTANCE = new DiffMergeReporter();
  public DiffMergeReporter()
  {
    super(DiffMergeMacOsReporter.INSTANCE, DiffMergeLinuxReporter.INSTANCE);
  }
}
