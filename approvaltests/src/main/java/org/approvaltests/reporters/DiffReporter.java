package org.approvaltests.reporters;

import org.approvaltests.reporters.intellij.IntelliJReporter;
import org.approvaltests.reporters.linux.ReportWithDiffToolOnLinux;
import org.approvaltests.reporters.macosx.ReportWithDiffToolOnMac;
import org.approvaltests.reporters.windows.ReportWithDiffToolOnWindows;

public class DiffReporter extends FirstWorkingReporter
{
  public static final DiffReporter INSTANCE = new DiffReporter();
  public DiffReporter()
  {
    super(IntelliJReporter.INSTANCE, ReportWithDiffToolOnWindows.INSTANCE, ReportWithDiffToolOnMac.INSTANCE, ReportWithDiffToolOnLinux.INSTANCE,
        JunitReporter.INSTANCE, QuietReporter.INSTANCE);
  }
}
