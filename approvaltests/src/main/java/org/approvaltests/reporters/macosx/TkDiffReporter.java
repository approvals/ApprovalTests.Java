package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.DiffPrograms.Mac;
import org.approvaltests.reporters.GenericDiffReporter;

public class TkDiffReporter extends GenericDiffReporter
{
  public static final TkDiffReporter INSTANCE = new TkDiffReporter();
  public TkDiffReporter()
  {
    super(Mac.TK_DIFF);
  }
}
