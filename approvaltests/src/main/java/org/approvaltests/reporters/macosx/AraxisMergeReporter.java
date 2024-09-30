package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.DiffPrograms;
import org.approvaltests.reporters.GenericDiffReporter;

public class AraxisMergeReporter extends GenericDiffReporter
{
  public static final AraxisMergeReporter INSTANCE = new AraxisMergeReporter();
  public AraxisMergeReporter()
  {
    super(DiffPrograms.Mac.ARAXIS_MERGE);
  }
}
