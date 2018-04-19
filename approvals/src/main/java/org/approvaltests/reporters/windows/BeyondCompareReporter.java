package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.FirstWorkingReporter;

public class BeyondCompareReporter extends FirstWorkingReporter
{
  public static final BeyondCompareReporter INSTANCE = new BeyondCompareReporter();
  public BeyondCompareReporter()
  {
    super(BeyondCompare4Reporter.INSTANCE, BeyondCompare3Reporter.INSTANCE);
  }
}
