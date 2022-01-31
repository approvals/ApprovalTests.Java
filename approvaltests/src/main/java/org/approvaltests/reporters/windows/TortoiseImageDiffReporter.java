package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.DiffPrograms.Windows;
import org.approvaltests.reporters.GenericDiffReporter;

public class TortoiseImageDiffReporter extends GenericDiffReporter
{
  public static final TortoiseImageDiffReporter INSTANCE = new TortoiseImageDiffReporter();
  public TortoiseImageDiffReporter()
  {
    super(Windows.TORTOISE_IMAGE_DIFF);
  }
}
