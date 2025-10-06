package org.approvaltests.inline

import org.approvaltests.Approvals
import org.approvaltests.core.Options
import org.approvaltests.reporters.BeyondCompareReporter
import org.junit.jupiter.api.Test

class KotlinInlineApprovalsTest {

    @Test
    fun testInlineApproval() {

        Approvals.verify("hello world", Options().inline("")
            .withReporter(InlineKotlinReporter(BeyondCompareReporter(), null)))
    }
}
