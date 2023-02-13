package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.FirstWorkingReporter;
import org.approvaltests.reporters.macosx.BeyondCompareMacReporter;

public class BeyondCompareReporter extends FirstWorkingReporter
{
  public static final BeyondCompareReporter INSTANCE = new BeyondCompareReporter();
  public BeyondCompareReporter()
  {
    super(BeyondCompare4Reporter.INSTANCE, BeyondCompare3Reporter.INSTANCE, BeyondCompareMacReporter.INSTANCE);
  }
}
