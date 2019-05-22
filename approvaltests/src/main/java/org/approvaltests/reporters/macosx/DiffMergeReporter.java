package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.DiffPrograms.Mac;

public class DiffMergeReporter extends GenericDiffReporter
{
  public static final DiffMergeReporter INSTANCE = new DiffMergeReporter();
  public DiffMergeReporter()
  {
    super(Mac.DIFF_MERGE);
  }
}