package org.approvaltests.reporters;

import org.approvaltests.reporters.windows.ReportWithTortoiseGitImageDiffWindows;
import org.approvaltests.reporters.windows.ReportWithTortoiseImageDiffWindows;

public class ImageReporter extends FirstWorkingReporter
{
  public ImageReporter()
  {
    super(ReportWithTortoiseImageDiffWindows.INSTANCE, ReportWithTortoiseGitImageDiffWindows.INSTANCE,
        ReportWithBeyondCompare.INSTANCE, ReportWithKaleidoscope.INSTANCE, ImageWebReporter.INSTANCE,
        QuietReporter.INSTANCE);
  }
}
