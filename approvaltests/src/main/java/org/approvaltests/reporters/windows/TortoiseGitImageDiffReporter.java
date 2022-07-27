package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.DiffPrograms.Windows;
import org.approvaltests.reporters.GenericDiffReporter;

public class TortoiseGitImageDiffReporter extends GenericDiffReporter
{
  public static final TortoiseGitImageDiffReporter INSTANCE = new TortoiseGitImageDiffReporter();
  public TortoiseGitImageDiffReporter()
  {
    super(Windows.TORTOISE_GIT_IMAGE_DIFF);
  }
}
