package org.approvaltests.combinations;

import org.junit.jupiter.api.Test;

public class PairWiseTest {

    @Test
    public void testPairs()
    {
        PairWiseAppprovals.verifyBestCoveringPairs((a,b,c,d) -> "",
                new Integer[]{1, 2, 3, 4, 5},
                new String[]{"a", "b", "c", "d"},
                new String[]{"L","M","N","O","P"},
                new Double[]{1.1,2.2,3.3,4.4});
    }
}
