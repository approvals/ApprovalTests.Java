package org.approvaltests.reporters.macosx;

import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.macosx.DiffPrograms.Mac;

public class TkDiffReporter extends GenericDiffReporter
{
  public static final TkDiffReporter INSTANCE = new TkDiffReporter();
  public TkDiffReporter()
  {
    super(Mac.TK_DIFF);
  }
}
