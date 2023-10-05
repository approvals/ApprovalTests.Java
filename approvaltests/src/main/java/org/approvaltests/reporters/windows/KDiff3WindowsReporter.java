package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.DiffPrograms.Windows;
import org.approvaltests.reporters.GenericDiffReporter;

public class KDiff3WindowsReporter extends GenericDiffReporter
{
  public static final KDiff3WindowsReporter INSTANCE = new KDiff3WindowsReporter();
  public KDiff3WindowsReporter()
  {
    super(Windows.KDIFF3);
  }
}
