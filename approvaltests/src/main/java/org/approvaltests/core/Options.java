package org.approvaltests.core;

import org.approvaltests.ReporterFactory;

import java.util.Optional;

public class Options {

    private Optional<ApprovalFailureReporter> reporter;

    public Options(ApprovalFailureReporter reporter) {
        this.reporter = Optional.ofNullable(reporter);
    }

    public Options() {
        this.reporter = Optional.empty();
    }

    public ApprovalFailureReporter getReporter() {
        return this.reporter.orElse(ReporterFactory.get());
    }

    public Options withReporter(ApprovalFailureReporter reporter) {
        return new Options(reporter);
    }
}
