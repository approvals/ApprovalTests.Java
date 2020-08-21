package org.approvaltests.combinations;

import org.approvaltests.strings.Printable;
import org.junit.jupiter.api.Test;

public class PrintableTest {
    @Test
    void testOverridingToString() {
        Integer p1[] = {1,2,3,4,5};
        CombinationApprovals.verifyAllCombinations(n -> n.get(), Printable.create(n-> "#"+n, p1));

        Printable<Integer> p2[] = Printable.create(n -> "#"+n, 1,2,3,4,5);
        CombinationApprovals.verifyAllCombinations(n -> n.get(), p2);
    }
    @Test
    void testLabels() {
        Printable<Integer> p[] = Printable.with()
                .label(1,"first")
                .label(2,"second")
                .label(3,"third")
                .label(4, "forth")
                .label(5, "fifth")
                .toArray();
        CombinationApprovals.verifyAllCombinations(n -> n.get(), p);
    }
}
