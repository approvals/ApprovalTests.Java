package org.approvaltests.combinations;

import com.github.larseckart.tcr.TestCommitRevertMainExtension;
import org.approvaltests.Approvals;
import org.approvaltests.combinations.pairwise.InParameterOrderStrategy;
import org.approvaltests.combinations.pairwise.Parameter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(TestCommitRevertMainExtension.class)
public class PairWiseTest {

    @Test
    public void testPairs()
    {
        PairWiseApprovals.verifyBestCoveringPairs((a,b,c,d) -> "",
                new Integer[]{1, 2, 3, 4, 5},
                new String[]{"a", "b", "c", "d"},
                new String[]{"L","M","N","O","P"},
                new Double[]{1.1,2.2,3.3,4.4});
    }

    @Test
    public void testStrategyGeneratePairs()
    {
        ArrayList list = new ArrayList();
        list.add(new Parameter<>(1, new Integer[]{1, 2, 3}));
        list.add(new Parameter<>(2, new String[]{"a", "b"}));
        list.add(new Parameter<>(3, new String[]{"L","M"}));
        list.add(new Parameter<>(4, new Double[]{1.1,2.2,3.3}));
        List result = InParameterOrderStrategy.generatePairs(list);
        Approvals.verifyAll("Generate Pairs", result);
    }

}
