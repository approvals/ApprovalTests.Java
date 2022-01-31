package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.DiffPrograms.Windows;
import org.approvaltests.reporters.GenericDiffReporter;

public class BeyondCompare4Reporter extends GenericDiffReporter
{
  public static final BeyondCompare4Reporter INSTANCE = new BeyondCompare4Reporter();
  public BeyondCompare4Reporter()
  {
    super(Windows.BEYOND_COMPARE_4);
  }
}
