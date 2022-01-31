package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.DiffPrograms.Windows;
import org.approvaltests.reporters.GenericDiffReporter;

public class WinMergeReporter extends GenericDiffReporter
{
  public static final WinMergeReporter INSTANCE = new WinMergeReporter();;
  public WinMergeReporter()
  {
    super(Windows.WIN_MERGE_REPORTER);
  }
}
