package org.approvaltests.reporters;

import com.spun.util.ClassUtils;
import com.spun.util.MapBuilder;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.reporters.intellij.IntelliJReporter;
import org.approvaltests.reporters.linux.MeldMergeReporter;
import org.approvaltests.reporters.macosx.KaleidoscopeDiffReporter;
import org.approvaltests.reporters.macosx.P4MergeReporter;
import org.approvaltests.reporters.macosx.TkDiffReporter;
import org.approvaltests.reporters.windows.AraxisMergeReporter;
import org.approvaltests.reporters.windows.CodeCompareReporter;
import org.approvaltests.reporters.windows.TortoiseDiffReporter;
import org.approvaltests.reporters.windows.WinMergeReporter;
import org.approvaltests.reporters.windows.WindowsDiffReporter;
import org.lambda.functions.Function1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class EnvironmentVariableReporter implements ApprovalFailureReporter
{
  private final ApprovalFailureReporter                                      reporter;
  // @formatter:off
  private static final Map<String, Class<? extends ApprovalFailureReporter>> REPORTER_MAP =
          new MapBuilder("AraxisMergeReporter", AraxisMergeReporter.class)
          .and("AutoApproveReporter", AutoApproveReporter.class)
          .and("AutoApproveWhenEmptyReporter", AutoApproveWhenEmptyReporter.class)
          .and("BeyondCompareReporter", BeyondCompareReporter.class)
          .and("ClipboardReporter", ClipboardReporter.class)
          .and("CodeCompareReporter", CodeCompareReporter.class)
          .and("DelayedClipboardReporter", DelayedClipboardReporter.class)
          .and("DiffMergeReporter", DiffMergeReporter.class)
          .and("DiffReporter", DiffReporter.class)
          .and("FileCaptureReporter", FileCaptureReporter.class)
          .and("ImageReporter", ImageReporter.class)
          .and("ImageWebReporter", ImageWebReporter.class)
          .and("IntelliJReporter", IntelliJReporter.class)
          .and("JunitReporter", JunitReporter.class)
          .and("KDiff3Reporter", KDiff3Reporter.class)
          .and("KaleidoscopeDiffReporter", KaleidoscopeDiffReporter.class)
          .and("MeldMergeReporter", MeldMergeReporter.class)
          .and("P4MergeReporter", P4MergeReporter.class)
          .and("PitReporter", PitReporter.class)
          .and("QuietReporter", QuietReporter.class)
          .and("TestNgReporter", TestNgReporter.class)
          .and("TextWebReporter", TextWebReporter.class)
          .and("TkDiffReporter", TkDiffReporter.class)
          .and("TortoiseDiffReporter", TortoiseDiffReporter.class)
          .and("VisualStudioCodeReporter", VisualStudioCodeReporter.class)
          .and("WinMergeReporter", WinMergeReporter.class)
          .and("WindowsDiffReporter", WindowsDiffReporter.class);
    // @formatter:on
  public static final String                                                 ENVIRONMENT_VARIABLE_NAME = "APPROVAL_TESTS_USE_REPORTER";
  public static Function1<String, String>                                    ENVIRONMENT_VARIABLES     = System::getenv;
  public static final EnvironmentVariableReporter                            INSTANCE                  = new EnvironmentVariableReporter();
  public EnvironmentVariableReporter()
  {
    String environmentValue = ENVIRONMENT_VARIABLES.call(ENVIRONMENT_VARIABLE_NAME);
    if (environmentValue == null)
    {
      reporter = null;
      return;
    }
    List<ApprovalFailureReporter> reporters = Arrays.stream(environmentValue.split(",")).distinct()
        .map(REPORTER_MAP::get).filter(Objects::nonNull)
        .map(reporterType -> (ApprovalFailureReporter) ClassUtils.create(reporterType))
        .collect(Collectors.toList());
    switch (reporters.size())
    {
      case 0 : {
        reporter = null;
        break;
      }
      case 1 : {
        reporter = reporters.get(0);
        break;
      }
      default : {
        reporter = new MultiReporter(reporters);
        break;
      }
    }
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
