package org.lambda.utils;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

public class AsErrorsTest {
    @Test
    void testFunction1() {
        Approvals.verifyException(() -> As.error(m -> {throw new Exception("Checked exception" + m);}).call("a"));
        Approvals.verifyException(() -> As.error((m, p2) -> {throw new Exception("Checked exception" + m);}).call("a", 2));
        Approvals.verifyException(() -> As.error((m, p2, p3) -> {throw new Exception("Checked exception" + m);}).call("a", 2, 3));
        Approvals.verifyException(() -> As.error((m, p2, p3, p4) -> {throw new Exception("Checked exception" + m);}).call("a", 2, 3, 4));
        Approvals.verifyException(() -> As.error((m, p2, p3, p4, p5) -> {throw new Exception("Checked exception" + m);}).call("a", 2, 3, 4, 5));
        Approvals.verifyException(() -> As.error((m, p2, p3, p4, p5, p6) -> {throw new Exception("Checked exception" + m);}).call("a", 2, 3, 4, 5, 6));
        Approvals.verifyException(() -> As.error((m, p2, p3, p4, p5, p6, p7) -> {throw new Exception("Checked exception" + m);}).call("a", 2, 3, 4, 5, 6, 7));
        Approvals.verifyException(() -> As.error((m, p2, p3, p4, p5, p6, p7, p8) -> {throw new Exception("Checked exception" + m);}).call("a", 2, 3, 4, 5, 6, 7, 8));
        Approvals.verifyException(() -> As.error((m, p2, p3, p4, p5, p6, p7, p8, p9) -> {throw new Exception("Checked exception" + m);}).call("a", 2, 3, 4, 5, 6, 7, 8, 9));
    }
}
