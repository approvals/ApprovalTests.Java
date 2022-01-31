package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.DiffPrograms.Windows;
import org.approvaltests.reporters.GenericDiffReporter;

public class TortoiseTextDiffReporter extends GenericDiffReporter
{
  public static final TortoiseTextDiffReporter INSTANCE = new TortoiseTextDiffReporter();
  public TortoiseTextDiffReporter()
  {
    super(Windows.TORTOISE_TEXT_DIFF);
  }
}
