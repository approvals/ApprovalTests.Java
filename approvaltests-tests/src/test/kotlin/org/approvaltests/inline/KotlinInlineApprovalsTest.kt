package org.approvaltests.inline

import org.approvaltests.Approvals
import org.approvaltests.core.Options
import org.approvaltests.inline.InlineKotlinReporter
import org.approvaltests.reporters.BeyondCompareReporter
import org.junit.jupiter.api.Test

class KotlinInlineApprovalsTest {

    @Test
    fun testInlineApproval() {
        val expected = """
            hello world
            """.trimIndent()
        Approvals.verify("hello world", Options().inline(expected))
    }
}