package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.DiffPrograms.Mac;
import org.approvaltests.reporters.GenericDiffReporter;

public class KaleidoscopeDiffReporter extends GenericDiffReporter
{
  public static final KaleidoscopeDiffReporter INSTANCE = new KaleidoscopeDiffReporter();
  public KaleidoscopeDiffReporter()
  {
    super(Mac.KALEIDOSCOPE);
  }
}
