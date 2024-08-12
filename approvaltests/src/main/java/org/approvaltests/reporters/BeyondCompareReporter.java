package org.approvaltests.reporters;

import org.approvaltests.reporters.macosx.BeyondCompareMacReporter;

public class BeyondCompareReporter extends FirstWorkingReporter
{
  public static final BeyondCompareReporter INSTANCE = new BeyondCompareReporter();
  public BeyondCompareReporter()
  {
    super(org.approvaltests.reporters.windows.BeyondCompareReporter.INSTANCE, BeyondCompareMacReporter.INSTANCE);
  }
}
