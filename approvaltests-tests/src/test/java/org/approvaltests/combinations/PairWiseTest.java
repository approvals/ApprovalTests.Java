package org.approvaltests.combinations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.approvaltests.Approvals;
import org.approvaltests.combinations.pairwise.Case;
import org.approvaltests.combinations.pairwise.InParameterOrderStrategy;
import org.approvaltests.combinations.pairwise.Parameter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.github.larseckart.tcr.TestCommitRevertMainExtension;

@ExtendWith(TestCommitRevertMainExtension.class)
public class PairWiseTest
{
  @Test
  public void testPairs()
  {
    PairWiseApprovals.verifyBestCoveringPairs((a, b, c, d) -> "", new Integer[]{1, 2, 3, 4, 5},
        new String[]{"a", "b", "c", "d"}, new String[]{"L", "M", "N", "O", "P"}, new Double[]{1.1, 2.2, 3.3, 4.4});
  }
  @Test
  public void testStrategyGeneratePairs()
  {
    ArrayList list = new ArrayList();
    list.add(new Parameter<>(1, new Integer[]{1, 2, 3}));
    list.add(new Parameter<>(2, new String[]{"a", "b"}));
    list.add(new Parameter<>(3, new String[]{"L", "M"}));
    list.add(new Parameter<>(4, new Double[]{1.1, 2.2, 3.3}));
    List result = InParameterOrderStrategy.generatePairs(list);
    Approvals.verifyAll("Generate Pairs", result);
  }
  @Test
  public void testRemoveDuplicateCases()
  {
    List<Case> cases = new ArrayList<>();
    cases.add(new Case(1, "Fred", false));
    cases.add(new Case(2, "Sam", true));
    cases.add(new Case(1, "Fred", false));
    String output = String.format("given cases \n%s\n", toString(cases));
    List<Case> result = InParameterOrderStrategy.verticalGrowth(cases);
    output += "\nvertical Growth results in\n" + toString(result);
    Approvals.verify(output);
  }

  public String toString(List<Case> result) {
    return result.stream().map(Case::toString).collect(Collectors.joining("\n"));
  }
}
