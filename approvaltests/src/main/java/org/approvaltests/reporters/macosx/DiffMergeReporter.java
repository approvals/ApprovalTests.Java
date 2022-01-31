package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.DiffPrograms.Mac;
import org.approvaltests.reporters.GenericDiffReporter;

public class DiffMergeReporter extends GenericDiffReporter
{
  public static final DiffMergeReporter INSTANCE = new DiffMergeReporter();
  public DiffMergeReporter()
  {
    super(Mac.DIFF_MERGE);
  }
}
