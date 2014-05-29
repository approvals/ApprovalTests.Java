package org.approvaltests.reporters.linux;

import org.approvaltests.reporters.FirstWorkingReporter;

public class LinuxReporter extends FirstWorkingReporter
{
  public static final LinuxReporter INSTANCE = new LinuxReporter();
  public LinuxReporter()
  {
    super(MeldReporter.INSTANCE);
  }
}
