package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.FirstWorkingReporter;

public class MacDiffReporter extends FirstWorkingReporter
{
  public static final MacDiffReporter INSTANCE = new MacDiffReporter();
  public MacDiffReporter()
  {
    super(DiffMergeReporter.INSTANCE, KaleidoscopeDiffReporter.INSTANCE, TkDiffReporter.INSTANCE);
  }
}
