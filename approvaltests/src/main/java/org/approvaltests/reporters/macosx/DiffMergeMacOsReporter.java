package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.DiffPrograms.Mac;
import org.approvaltests.reporters.GenericDiffReporter;

public class DiffMergeMacOsReporter extends GenericDiffReporter
{
  public static final DiffMergeMacOsReporter INSTANCE = new DiffMergeMacOsReporter();
  public DiffMergeMacOsReporter()
  {
    super(Mac.DIFF_MERGE);
  }
}
