package org.approvaltests.reporters;

import com.spun.util.ClassUtils;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.reporters.intellij.IntelliJReporter;
import org.approvaltests.reporters.linux.MeldMergeReporter;
import org.approvaltests.reporters.macosx.KaleidoscopeDiffReporter;
import org.approvaltests.reporters.macosx.P4MergeReporter;
import org.approvaltests.reporters.macosx.TkDiffReporter;
import org.approvaltests.reporters.windows.*;

import java.util.*;

public class EnvironmentVariableReporter implements ApprovalFailureReporter {
    private final ApprovalFailureReporter reporter;

    private static final Map<String, Class<? extends ApprovalFailureReporter>> REPORTER_MAP = Map.ofEntries(
            Map.entry("AraxisMergeReporter", AraxisMergeReporter.class),
            Map.entry("AutoApproveReporter", AutoApproveReporter.class),
            Map.entry("AutoApproveWhenEmptyReporter", AutoApproveWhenEmptyReporter.class),
            Map.entry("BeyondCompareReporter", BeyondCompareReporter.class),
            Map.entry("ClipboardReporter", ClipboardReporter.class),
            Map.entry("CodeCompareReporter", CodeCompareReporter.class),
            Map.entry("DelayedClipboardReporter", DelayedClipboardReporter.class),
            Map.entry("DiffMergeReporter", DiffMergeReporter.class),
            Map.entry("DiffReporter", DiffReporter.class),
            Map.entry("FileCaptureReporter", FileCaptureReporter.class),
            Map.entry("ImageReporter", ImageReporter.class),
            Map.entry("ImageWebReporter", ImageWebReporter.class),
            Map.entry("IntelliJReporter", IntelliJReporter.class),
            Map.entry("JunitReporter", JunitReporter.class),
            Map.entry("KDiff3Reporter", KDiff3Reporter.class),
            Map.entry("KaleidoscopeDiffReporter", KaleidoscopeDiffReporter.class),
            Map.entry("MeldMergeReporter", MeldMergeReporter.class),
            Map.entry("P4MergeReporter", P4MergeReporter.class),
            Map.entry("PitReporter", PitReporter.class),
            Map.entry("QuietReporter", QuietReporter.class),
            Map.entry("TestNgReporter", TestNgReporter.class),
            Map.entry("TextWebReporter", TextWebReporter.class),
            Map.entry("TkDiffReporter", TkDiffReporter.class),
            Map.entry("TortoiseDiffReporter", TortoiseDiffReporter.class),
            Map.entry("VisualStudioCodeReporter", VisualStudioCodeReporter.class),
            Map.entry("WinMergeReporter", WinMergeReporter.class),
            Map.entry("WindowsDiffReporter", WindowsDiffReporter.class)
    );

    public static final String ENVIRONMENT_VARIABLE_NAME = "APPROVAL_TESTS_USE_REPORTER";
    public static final EnvironmentVariableReporter INSTANCE = new EnvironmentVariableReporter();

    public EnvironmentVariableReporter() {
        String environmentValue = System.getenv(ENVIRONMENT_VARIABLE_NAME);
        if(environmentValue == null) {
            reporter = null;
            return;
        }

        var reporters = Arrays.stream(environmentValue.split(","))
                .distinct()
                .map(REPORTER_MAP::get)
                .filter(Objects::nonNull)
                .map(reporterType -> (ApprovalFailureReporter) ClassUtils.create(reporterType))
                .toList();

        switch(reporters.size()) {
            case 0: {
                reporter = null;
                break;
            }
            case 1: {
                reporter = reporters.get(0);
                break;
            }
            default: {
                reporter = new MultiReporter(reporters);
                break;
            }
        }
    }

    public ApprovalFailureReporter getReporter() {
        return reporter;
    }

    @Override
    public boolean report(String received, String approved) {
        if(reporter == null) {
            return false;
        }

        return reporter.report(received, approved);
    }
}
