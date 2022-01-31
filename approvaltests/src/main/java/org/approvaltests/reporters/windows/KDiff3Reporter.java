package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.DiffPrograms.Windows;
import org.approvaltests.reporters.GenericDiffReporter;

public class KDiff3Reporter extends GenericDiffReporter
{
  public static final KDiff3Reporter INSTANCE = new KDiff3Reporter();
  public KDiff3Reporter()
  {
    super(Windows.KDIFF3);
  }
}
