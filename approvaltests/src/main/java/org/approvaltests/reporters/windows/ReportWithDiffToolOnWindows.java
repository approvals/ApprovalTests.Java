// Generated from diff_reporters.csv -- do not edit manually.
package org.approvaltests.reporters.windows;

import com.spun.util.SystemUtils;
import org.approvaltests.reporters.FirstWorkingReporter;

public class ReportWithDiffToolOnWindows extends FirstWorkingReporter
{
  public static final ReportWithDiffToolOnWindows INSTANCE = new ReportWithDiffToolOnWindows();
  public ReportWithDiffToolOnWindows()
  {
    super(
    // @formatter:off
        ReportWithBeyondCompare3Windows.INSTANCE,
        ReportWithBeyondCompare4Windows.INSTANCE,
        ReportWithBeyondCompare5Windows.INSTANCE,
        ReportWithTortoiseImageDiffWindows.INSTANCE,
        ReportWithTortoiseTextDiffWindows.INSTANCE,
        ReportWithTortoiseGitImageDiffWindows.INSTANCE,
        ReportWithTortoiseGitTextDiffWindows.INSTANCE,
        ReportWithWinMergeReporterWindows.INSTANCE,
        ReportWithAraxisMergeWindows.INSTANCE,
        ReportWithCodeCompareWindows.INSTANCE,
        ReportWithKdiff3Windows.INSTANCE,
        ReportWithVisualStudioCodeWindows.INSTANCE,
        ReportWithSublimeMergeWindows.INSTANCE
    // @formatter:on
    );
  }
  @Override
  public boolean report(String received, String approved)
  {
    if (!SystemUtils.isWindowsEnvironment())
    { return false; }
    return super.report(received, approved);
  }
}
