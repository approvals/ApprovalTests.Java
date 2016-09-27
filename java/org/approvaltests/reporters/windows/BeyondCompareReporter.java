package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.macosx.DiffPrograms.Windows;

public class BeyondCompareReporter extends GenericDiffReporter
{
  public static final BeyondCompareReporter INSTANCE = new BeyondCompareReporter();
  public BeyondCompareReporter()
  {
    super(Windows.BEYOND_COMPARE_3);
  }
}
