// Generated from diff_reporters.csv -- do not edit manually.
package org.approvaltests.reporters.linux;

import com.spun.util.SystemUtils;
import org.approvaltests.reporters.FirstWorkingReporter;

public class ReportWithDiffToolOnLinux extends FirstWorkingReporter
{
  public static final ReportWithDiffToolOnLinux INSTANCE = new ReportWithDiffToolOnLinux();
  public ReportWithDiffToolOnLinux()
  {
    super(
    // @formatter:off
        ReportWithDiffMergeLinux.INSTANCE,
        ReportWithMeldMergeLinux.INSTANCE,
        ReportWithKdiff3Linux.INSTANCE,
        ReportWithDiffCommandLineLinux.INSTANCE,
        ReportWithSublimeMergeLinux.INSTANCE
    // @formatter:on
    );
  }
  @Override
  public boolean report(String received, String approved)
  {
    if (SystemUtils.isWindowsEnvironment() || SystemUtils.isMacEnvironment())
    { return false; }
    return super.report(received, approved);
  }
}
