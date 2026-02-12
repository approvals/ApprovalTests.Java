// Generated from diff_reporters.csv -- do not edit manually.
package org.approvaltests.reporters.macosx;

import com.spun.util.SystemUtils;
import org.approvaltests.reporters.FirstWorkingReporter;

public class ReportWithDiffToolOnMac extends FirstWorkingReporter
{
  public static final ReportWithDiffToolOnMac INSTANCE = new ReportWithDiffToolOnMac();
  public ReportWithDiffToolOnMac()
  {
    super(
    // @formatter:off
        ReportWithDiffMergeMac.INSTANCE,
        ReportWithFileMergeMac.INSTANCE,
        ReportWithBeyondCompareMac.INSTANCE,
        ReportWithKaleidoscopeMac.INSTANCE,
        ReportWithKaleidoscope3Mac.INSTANCE,
        ReportWithKdiff3Mac.INSTANCE,
        ReportWithP4mergeMac.INSTANCE,
        ReportWithTkDiffMac.INSTANCE,
        ReportWithVisualStudioCodeMac.INSTANCE,
        ReportWithAraxisMergeMac.INSTANCE,
        ReportWithDiffCommandLineMac.INSTANCE,
        ReportWithSublimeMergeMac.INSTANCE
    // @formatter:on
    );
  }
  @Override
  public boolean report(String received, String approved)
  {
    if (!SystemUtils.isMacEnvironment())
    { return false; }
    return super.report(received, approved);
  }
}
