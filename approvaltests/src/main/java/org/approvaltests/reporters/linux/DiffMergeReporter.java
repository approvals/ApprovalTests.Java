package org.approvaltests.reporters.linux;

import org.approvaltests.reporters.DiffPrograms.Linux;
import org.approvaltests.reporters.GenericDiffReporter;

public class DiffMergeReporter extends GenericDiffReporter
{
  public static final DiffMergeReporter INSTANCE = new DiffMergeReporter();
  public DiffMergeReporter()
  {
    super(Linux.DIFF_MERGE);
  }
}