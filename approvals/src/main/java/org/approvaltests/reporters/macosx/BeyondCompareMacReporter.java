package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.macosx.DiffPrograms.Mac;

public class BeyondCompareMacReporter extends GenericDiffReporter
{
  public static final BeyondCompareMacReporter INSTANCE = new BeyondCompareMacReporter();
  public BeyondCompareMacReporter()
  {
    super(Mac.BEYOND_COMPARE);
  }
}