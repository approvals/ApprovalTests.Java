package org.approvaltests.core;

public class Options {

    private ApprovalFailureReporter reporter;

    public Options(ApprovalFailureReporter reporter) {
        this.reporter = reporter;
    }

    public ApprovalFailureReporter getReporter() {
        return this.reporter;
    }
}
