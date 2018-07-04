package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.macosx.DiffPrograms.Mac;

public class KaleidoscopeDiffReporter extends GenericDiffReporter
{
  public static final KaleidoscopeDiffReporter INSTANCE = new KaleidoscopeDiffReporter();
  public KaleidoscopeDiffReporter()
  {
    super(Mac.KALEIDOSCOPE);
  }
}
