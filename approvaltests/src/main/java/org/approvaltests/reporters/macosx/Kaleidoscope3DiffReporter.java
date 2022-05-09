package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.DiffPrograms.Mac;
import org.approvaltests.reporters.GenericDiffReporter;

public class Kaleidoscope3DiffReporter extends GenericDiffReporter
{
  public static final Kaleidoscope3DiffReporter INSTANCE = new Kaleidoscope3DiffReporter();
  public Kaleidoscope3DiffReporter()
  {
    super(Mac.KALEIDOSCOPE3);
  }
}
