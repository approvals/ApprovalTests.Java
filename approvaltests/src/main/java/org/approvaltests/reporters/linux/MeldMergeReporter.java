package org.approvaltests.reporters.linux;

import org.approvaltests.reporters.DiffPrograms.Linux;
import org.approvaltests.reporters.GenericDiffReporter;

public class MeldMergeReporter extends GenericDiffReporter
{
  public static final MeldMergeReporter INSTANCE = new MeldMergeReporter();
  public MeldMergeReporter()
  {
    super(Linux.MELD_MERGE);
  }
}