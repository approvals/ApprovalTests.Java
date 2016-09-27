package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.macosx.DiffPrograms.Mac;

public class P4MergeReporter extends GenericDiffReporter
{
  public static final P4MergeReporter INSTANCE = new P4MergeReporter();
  public P4MergeReporter()
  {
    super(Mac.P4MERGE);
  }
}
