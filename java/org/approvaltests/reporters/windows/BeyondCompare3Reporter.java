package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.macosx.DiffPrograms.Windows;

public class BeyondCompare3Reporter extends GenericDiffReporter
{
  public static final BeyondCompare3Reporter INSTANCE = new BeyondCompare3Reporter();
  public BeyondCompare3Reporter()
  {
    super(Windows.BEYOND_COMPARE_3);
  }
}
