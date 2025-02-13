package org.approvaltests.reporters;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.core.VerifyResult;
import org.junit.jupiter.api.Test;

class FirstWorkingReporterTest {

    @Test
    void testReporterWithApprovalPower() {
        var t = new ReporterWithApprovalPower() {

            @Override
            public boolean report(String received, String approved) {
                return true;
            }

            @Override
            public VerifyResult approveWhenReported() {
                return VerifyResult.SUCCESS;
            }
        };
        Approvals.verify("test", new Options(new FirstWorkingReporter(t)));
    }
}