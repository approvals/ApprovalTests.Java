package org.lambda.utils;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;
import org.lambda.actions.Actions;
import org.lambda.functions.Functions;

public class AsErrorsTest {
    @Test
    void testFunction1() {
        Approvals.verifyException(() -> Functions.unchecked(m -> returnException(m)).call("a"));
        Approvals.verifyException(() -> Functions.unchecked((m, p2) -> returnException(m)).call("a", 2));
        Approvals.verifyException(() -> Functions.unchecked((m, p2, p3) -> returnException(m)).call("a", 2, 3));
        Approvals.verifyException(() -> Functions.unchecked((m, p2, p3, p4) -> returnException(m)).call("a", 2, 3, 4));
        Approvals.verifyException(() -> Functions.unchecked((m, p2, p3, p4, p5) -> returnException(m)).call("a", 2, 3, 4, 5));
        Approvals.verifyException(() -> Functions.unchecked((m, p2, p3, p4, p5, p6) -> returnException(m)).call("a", 2, 3, 4, 5, 6));
        Approvals.verifyException(() -> Functions.unchecked((m, p2, p3, p4, p5, p6, p7) -> returnException(m)).call("a", 2, 3, 4, 5, 6, 7));
        Approvals.verifyException(() -> Functions.unchecked((m, p2, p3, p4, p5, p6, p7, p8) -> returnException(m)).call("a", 2, 3, 4, 5, 6, 7, 8));
        Approvals.verifyException(() -> Functions.unchecked((m, p2, p3, p4, p5, p6, p7, p8, p9) -> returnException(m)).call("a", 2, 3, 4, 5, 6, 7, 8, 9));
        Approvals.verifyException(() -> Actions.unchecked(m -> returnException(m)).call("a"));
        Approvals.verifyException(() -> Actions.unchecked((m, p2) -> returnException(m)).call("a", 2));
        Approvals.verifyException(() -> Actions.unchecked((m, p2, p3) -> returnException(m)).call("a", 2, 3));
        Approvals.verifyException(() -> Actions.unchecked((m, p2, p3, p4) -> returnException(m)).call("a", 2, 3, 4));
        Approvals.verifyException(() -> Actions.unchecked((m, p2, p3, p4, p5) -> returnException(m)).call("a", 2, 3, 4, 5));
        Approvals.verifyException(() -> Actions.unchecked((m, p2, p3, p4, p5, p6) -> returnException(m)).call("a", 2, 3, 4, 5, 6));
        Approvals.verifyException(() -> Actions.unchecked((m, p2, p3, p4, p5, p6, p7) -> returnException(m)).call("a", 2, 3, 4, 5, 6, 7));
        Approvals.verifyException(() -> Actions.unchecked((m, p2, p3, p4, p5, p6, p7, p8) -> returnException(m)).call("a", 2, 3, 4, 5, 6, 7, 8));
        Approvals.verifyException(() -> Actions.unchecked((m, p2, p3, p4, p5, p6, p7, p8, p9) -> returnException(m)).call("a", 2, 3, 4, 5, 6, 7, 8, 9));
    }

    private Object returnException(Object m) throws Exception {
        throw new Exception("Checked exception" + m);
    }
}
