package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.DiffPrograms.Mac;
import org.approvaltests.reporters.GenericDiffReporter;

public class KDiff3Reporter extends GenericDiffReporter
{
  public static final KDiff3Reporter INSTANCE = new KDiff3Reporter();
  public KDiff3Reporter()
  {
    super(Mac.KDIFF3);
  }
}
