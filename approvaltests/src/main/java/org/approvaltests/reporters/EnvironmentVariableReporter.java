package org.approvaltests.reporters;

import com.spun.util.ClassUtils;
import com.spun.util.MapBuilder;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.reporters.intellij.IntelliJReporter;
import org.approvaltests.reporters.linux.ReportWithDiffToolOnLinux;
import org.approvaltests.reporters.linux.ReportWithMeldMergeLinux;
import org.approvaltests.reporters.macosx.ReportWithDiffToolOnMac;
import org.approvaltests.reporters.macosx.ReportWithP4mergeMac;
import org.approvaltests.reporters.macosx.ReportWithTkDiffMac;
import org.approvaltests.reporters.windows.ReportWithCodeCompareWindows;
import org.approvaltests.reporters.windows.ReportWithDiffToolOnWindows;
import org.approvaltests.reporters.windows.ReportWithWinMergeReporterWindows;
import org.lambda.functions.Function1;
import org.lambda.query.Queryable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EnvironmentVariableReporter implements ApprovalFailureReporter
{
  // @formatter:off
  private static final Map<String, Class<? extends ApprovalFailureReporter>> REPORTER_MAP =
          new MapBuilder("AraxisMergeReporter", ReportWithAraxisMerge.class)
          .and("AutoApproveReporter", AutoApproveReporter.class)
          .and("AutoApproveWhenEmptyReporter", AutoApproveWhenEmptyReporter.class)
          .and("BeyondCompareReporter", ReportWithBeyondCompare.class)
          .and("ClipboardReporter", ClipboardReporter.class)
          .and("CodeCompareReporter", ReportWithCodeCompareWindows.class)
          .and("DelayedClipboardReporter", DelayedClipboardReporter.class)
          .and("DiffMergeReporter", ReportWithDiffMerge.class)
          .and("DiffReporter", DiffReporter.class)
          .and("FileCaptureReporter", FileCaptureReporter.class)
          .and("ImageReporter", ImageReporter.class)
          .and("ImageWebReporter", ImageWebReporter.class)
          .and("IntelliJReporter", IntelliJReporter.class)
          .and("JunitReporter", JunitReporter.class)
          .and("KDiff3Reporter", ReportWithKdiff3.class)
          .and("KaleidoscopeDiffReporter", ReportWithKaleidoscope.class)
          .and("MeldMergeReporter", ReportWithMeldMergeLinux.class)
          .and("P4MergeReporter", ReportWithP4mergeMac.class)
          .and("PitReporter", PitReporter.class)
          .and("QuietReporter", QuietReporter.class)
          .and("TestNgReporter", TestNgReporter.class)
          .and("TextWebReporter", TextWebReporter.class)
          .and("TkDiffReporter", ReportWithTkDiffMac.class)
          .and("TortoiseDiffReporter", ReportWithTortoise.class)
          .and("VisualStudioCodeReporter", ReportWithVisualStudioCode.class)
          .and("WinMergeReporter", ReportWithWinMergeReporterWindows.class)
          .and("WindowsDiffReporter", ReportWithDiffToolOnWindows.class);
    // @formatter:on
  public static final String                                                 ENVIRONMENT_VARIABLE_NAME = "APPROVAL_TESTS_USE_REPORTER";
  public static Function1<String, String>                                    ENVIRONMENT_VARIABLES     = System::getenv;
  public static final EnvironmentVariableReporter                            INSTANCE                  = new EnvironmentVariableReporter();
  private ApprovalFailureReporter                                            reporter                  = null;
  public EnvironmentVariableReporter()
  {
    String environmentValue = ENVIRONMENT_VARIABLES.call(ENVIRONMENT_VARIABLE_NAME);
    if (environmentValue == null)
    { return; }
    Queryable<String> split = Queryable.of(environmentValue.split(","));
    Queryable<ApprovalFailureReporter> reporters = split.distinct().select(REPORTER_MAP::get)
        .where(Objects::nonNull).select(reporterType -> (ApprovalFailureReporter) ClassUtils.create(reporterType));
    reporter = reporters.size() == 1 ? reporters.first() : new MultiReporter(reporters);
  }

  public ApprovalFailureReporter getReporter()
  {
    return reporter;
  }

  @Override
  public boolean report(String received, String approved)
  {
    if (reporter == null)
    { return false; }
    return reporter.report(received, approved);
  }

  public Map<String, Class> getReporterMapping()
  {
    return new HashMap<>(REPORTER_MAP);
  }
}
