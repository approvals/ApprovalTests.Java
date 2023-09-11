package org.approvaltests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApprovalsDuplicateVerifyExceptionTest {

    @Test
    void testExceptionMessage() {
        Approvals.verify(new ApprovalsDuplicateVerifyException("file.txt"));
    }
}
