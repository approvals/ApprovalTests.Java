package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.macosx.DiffPrograms.Windows;

public class AraxisMergeReporter extends GenericDiffReporter
{
  public static final AraxisMergeReporter INSTANCE = new AraxisMergeReporter();
  public AraxisMergeReporter()
  {
    super(Windows.ARAXIS_MERGE);
  }
}
