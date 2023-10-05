package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.DiffPrograms.Mac;
import org.approvaltests.reporters.GenericDiffReporter;

public class KDiff3MacReporter extends GenericDiffReporter
{
  public static final KDiff3MacReporter INSTANCE = new KDiff3MacReporter();
  public KDiff3MacReporter()
  {
    super(Mac.KDIFF3);
  }
}
