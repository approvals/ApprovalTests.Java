package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.FirstWorkingReporter;

public class TortoiseDiffReporter extends FirstWorkingReporter
{
  public static final TortoiseDiffReporter INSTANCE = new TortoiseDiffReporter();
  public TortoiseDiffReporter()
  {
    super(TortoiseTextDiffReporter.INSTANCE, TortoiseImageDiffReporter.INSTANCE);
  }
}
