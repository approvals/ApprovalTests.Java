package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.FirstWorkingReporter;

public class KaleidoscopeDiffReporter extends FirstWorkingReporter
{
  public static final KaleidoscopeDiffReporter INSTANCE = new KaleidoscopeDiffReporter();
  public KaleidoscopeDiffReporter()
  {
    super(LegacyKaleidoscopeDiffReporter.INSTANCE, Kaleidoscope3DiffReporter.INSTANCE);
  }
}
