package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.macosx.DiffPrograms.Windows;

public class TortoiseTextDiffReporter extends GenericDiffReporter
{
  public static final TortoiseTextDiffReporter INSTANCE = new TortoiseTextDiffReporter();
  public TortoiseTextDiffReporter()
  {
    super(Windows.TORTOISE_TEXT_DIFF);
  }
}
