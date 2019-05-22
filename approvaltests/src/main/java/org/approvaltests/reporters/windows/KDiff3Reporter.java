package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.DiffPrograms.Windows;

public class KDiff3Reporter extends GenericDiffReporter
{
  public static final KDiff3Reporter INSTANCE = new KDiff3Reporter();
  public KDiff3Reporter()
  {
    super(Windows.KDIFF3);
  }
}
