package org.approvaltests.combinations;

import com.github.larseckart.tcr.TestCommitRevertMainExtension;
import org.approvaltests.Approvals;
import org.approvaltests.combinations.pairwise.Case;
import org.approvaltests.combinations.pairwise.InParameterOrderStrategy;
import org.approvaltests.combinations.pairwise.Parameter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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

    @Test
    public void testVerticalGrowth()
    {
        List<Case> cases = new ArrayList<>();
        LinkedHashMap<String, Object> prototype = new LinkedHashMap<>();
        prototype.put("1", "one");
        prototype.put("2", "two");
        prototype.put("3", "three");
        prototype.put("4", "four");
        prototype.put("5", "five");
        prototype.put("21", "one");
        prototype.put("22", "two");
        prototype.put("23", "three");
        prototype.put("24", "four");
        prototype.put("25", "five");
        prototype.put("31", "one");
        prototype.put("32", "two");
        prototype.put("33", "three");
        prototype.put("34", "four");
        prototype.put("35", "five");
        Case aCase = new Case(prototype);
        cases.add(aCase);
        String output = String.format("given cases \n%s\n", cases);
        List result = InParameterOrderStrategy.verticalGrowth(cases);
        output += "verticalGrowth results in\n" + result;
        Approvals.verify(output);
    }

}
