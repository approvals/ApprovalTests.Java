package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.DiffPrograms.Mac;
import org.approvaltests.reporters.GenericDiffReporter;

public class BeyondCompareMacReporter extends GenericDiffReporter
{
  public static final BeyondCompareMacReporter INSTANCE = new BeyondCompareMacReporter();
  public BeyondCompareMacReporter()
  {
    super(Mac.BEYOND_COMPARE);
  }
}
