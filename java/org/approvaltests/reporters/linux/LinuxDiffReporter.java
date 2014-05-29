package org.approvaltests.reporters.linux;

import org.approvaltests.reporters.FirstWorkingReporter;

public class LinuxDiffReporter extends FirstWorkingReporter
{
  public static final LinuxDiffReporter INSTANCE = new LinuxDiffReporter();
  public LinuxDiffReporter()
  {
    super(MeldReporter.INSTANCE);
  }
}
