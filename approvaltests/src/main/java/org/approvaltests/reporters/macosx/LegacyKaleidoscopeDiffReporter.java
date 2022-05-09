package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.DiffPrograms.Mac;
import org.approvaltests.reporters.GenericDiffReporter;

public class LegacyKaleidoscopeDiffReporter extends GenericDiffReporter
{
  public static final LegacyKaleidoscopeDiffReporter INSTANCE = new LegacyKaleidoscopeDiffReporter();
  public LegacyKaleidoscopeDiffReporter()
  {
    super(Mac.KALEIDOSCOPE);
  }
}
