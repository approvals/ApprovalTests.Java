package org.approvaltests.reporters.linux;

import org.approvaltests.reporters.FirstWorkingReporter;
import org.approvaltests.reporters.windows.IntelliJReporter;

public class LinuxDiffReporter extends FirstWorkingReporter
{
  public static final LinuxDiffReporter INSTANCE = new LinuxDiffReporter();
  public LinuxDiffReporter()
  {
    super(
        // startcode linux_diff_reporters
        IntelliJReporter.INSTANCE
    // endcode
    );
  }
}
