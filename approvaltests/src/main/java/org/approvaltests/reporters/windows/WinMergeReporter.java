package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.DiffPrograms.Windows;

public class WinMergeReporter extends GenericDiffReporter
{
  public static final WinMergeReporter INSTANCE = new WinMergeReporter();;
  public WinMergeReporter()
  {
    super(Windows.WIN_MERGE_REPORTER);
  }
}
