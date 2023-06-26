package org.approvaltests.reporters.linux;

import org.approvaltests.reporters.DiffPrograms.Linux;
import org.approvaltests.reporters.GenericDiffReporter;

public class DiffMergeLinuxReporter extends GenericDiffReporter
{
  public static final DiffMergeLinuxReporter INSTANCE = new DiffMergeLinuxReporter();
  public DiffMergeLinuxReporter()
  {
    super(Linux.DIFF_MERGE);
  }
}
