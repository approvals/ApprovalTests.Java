package org.approvaltests;

import com.spun.util.Asserts;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.FileLauncherReporter;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.UseReporterTest;
import org.junit.jupiter.api.Test;

public class OptionsTest {

    // Call without passing an option
    // Call with option as last parameter
    // Pass options constructed with a Reporter
    // Pass options constructed with a builder pattern

    // crate TestReporter
    // verify with options of that getReporter
    // will fail
    // catch failure and
    // check that getReporter was called

    @Test
    void verifyWithReporterInOptions() {
        ApprovalFailureReporterSpy reporter = new ApprovalFailureReporterSpy();
        Options options = new Options(reporter);
        try {
            Approvals.verify("hallo", options);
        } catch (Error error) {
            // check that getReporter was called
            Asserts.assertTrue("the getReporter should've been called", reporter.hasBeenCalled());
        }
    }

    @Test
    void verifyWithReporterFromOptionsBuilder() {
        ApprovalFailureReporterSpy reporter = new ApprovalFailureReporterSpy();
        Options options = new Options().withReporter(reporter);
        try {
            Approvals.verify("hallo", options);
        } catch (Error error) {
            // check that getReporter was called
            Asserts.assertTrue("the getReporter should've been called", reporter.hasBeenCalled());
        }
    }

    @Test
    @UseReporter(UseReporterTest.TestReporter.class)
    void verifyWithoutReporter() {
        Options options = new Options();
        try {
            Approvals.verify("hallo", options);
        } catch (Error error) {
            // check that getReporter was called
            Asserts.assertEqual("the getReporter should've been called", "hallo\n", UseReporterTest.TestReporter.getLast());
        }
    }

    static class ApprovalFailureReporterSpy implements ApprovalFailureReporter {
        private boolean hasBeenCalled;

        public ApprovalFailureReporterSpy() {
            this.hasBeenCalled = false;
        }

        @Override
        public void report(String received, String approved) {
            hasBeenCalled = true;
        }

        public boolean hasBeenCalled() {
            return hasBeenCalled;
        }
    }
}


