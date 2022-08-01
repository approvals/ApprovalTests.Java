package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.DiffPrograms.Windows;
import org.approvaltests.reporters.GenericDiffReporter;

public class TortoiseGitTextDiffReporter extends GenericDiffReporter
{
  public static final TortoiseGitTextDiffReporter INSTANCE = new TortoiseGitTextDiffReporter();
  public TortoiseGitTextDiffReporter()
  {
    super(Windows.TORTOISE_GIT_TEXT_DIFF);
  }
}
