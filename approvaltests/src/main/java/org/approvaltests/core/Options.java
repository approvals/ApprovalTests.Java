package org.approvaltests.core;

public class Options {

    private ApprovalFailureReporter reporter;

    public Options(ApprovalFailureReporter reporter) {
        this.reporter = reporter;
    }

    public Options() {

    }

    public ApprovalFailureReporter getReporter() {
        return this.reporter;
    }

    public Options withReporter(ApprovalFailureReporter reporter) {
        return new Options(reporter);
    }
}
