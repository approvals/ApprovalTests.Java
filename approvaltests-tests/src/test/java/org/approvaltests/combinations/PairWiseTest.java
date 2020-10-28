package org.approvaltests.combinations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.approvaltests.Approvals;
import org.approvaltests.combinations.pairwise.Case;
import org.approvaltests.combinations.pairwise.InParameterOrderStrategy;
import org.approvaltests.combinations.pairwise.OptionsForAParameter;
import org.approvaltests.core.Options;
import org.approvaltests.scrubbers.RegExScrubber;
import org.junit.jupiter.api.Test;

//@ExtendWith(TestCommitRevertMainExtension.class)
public class PairWiseTest
{
  @Test
  public void testPairs()
  {
    PairWiseApprovals.verifyBestCoveringPairs((a, b, c, d) -> "", new Integer[]{1, 2, 3, 4, 5},
        new String[]{"a", "b", "c", "d"}, new String[]{"L", "M", "N", "O", "P"}, new Double[]{1.1, 2.2, 3.3, 4.4});
  }
  @Test
  public void testPairsScrubbed()
  {
    PairWiseApprovals.verifyBestCoveringPairs((a, b, c, d) -> "", new Integer[]{112, 111, 113, 114, 115},
        new Integer[]{221, 222, 223, 224}, new Integer[]{331, 332, 333, 334, 335},
        new Integer[]{441, 442, 443, 444},
        new Options().withScrubber(new RegExScrubber("\\d{3}", a -> "input-" + a)));
  }
  @Test
  public void testStrategyGeneratePairs()
  {
    ArrayList list = new ArrayList();
    list.add(new OptionsForAParameter<>(1, new Integer[]{1, 2, 3}));
    list.add(new OptionsForAParameter<>(2, new String[]{"a", "b"}));
    list.add(new OptionsForAParameter<>(3, new String[]{"L", "M"}));
    list.add(new OptionsForAParameter<>(4, new Double[]{1.1, 2.2, 3.3}));
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
  @Test
  void testHorizontalGrowth() throws Exception
  {
    ArrayList<Case> cases = new ArrayList<>();
    cases.add(new Case("Lars", 1, "Tartu"));
    cases.add(new Case("Lars", 1, "Tallinn"));
    cases.add(new Case("Lars", 2, "Tartu"));
    cases.add(new Case("Lars", 2, "Tallinn"));
    cases.add(new Case("Lars", 3, "Tartu"));
    cases.add(new Case("Lars", 3, "Tallinn"));
    ArrayList<Case> pairs = new ArrayList<>();
    pairs.add(new Case("Lars", 1, "Tartu"));
    pairs.add(new Case("Lars", 1, "Pärnu"));
    String pairsBefore = toString(pairs);
    String casesBefore = toString(cases);
    List<Case> result = InParameterOrderStrategy.horizontalGrowth(cases, pairs);
    Approvals
        .verify("Cases before: \n" + casesBefore + " \n Cases after: \n" + toString(cases) + "\nPairs before: \n"
            + pairsBefore + " \n Pairs after: \n" + toString(pairs) + "\n Result: \n" + toString(result));
  }
  // duplicate to 1st test, but with some result analysis
  @Test
  void testGeneratePairs() throws Exception
  {
    String[] names = {"Jupe", "Pete", "Bob"};
    String[] towns = {"Tartu", "Heidelberg"};
    String[] bloodTypes = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
    List<List<Case>> lists = generatePairs(names, towns, bloodTypes);
    final StringBuilder strb = new StringBuilder();
    lists.forEach(l -> strb.append(toString(l) + "\n\n"));
    // empty list, list with bloodtype+name, list with bloodtype+town and 3town+name cases
    Approvals.verify(strb.toString());
  }
  List<List<Case>> generatePairs(Object[]... parameters) throws Exception
  {
    ArrayList<OptionsForAParameter<?>> list = new ArrayList<>();
    for (int i = 0; i < parameters.length; i++)
    {
      list.add(new OptionsForAParameter<>(i, parameters[i]));
    }
    return InParameterOrderStrategy.generatePairs(list);
  }
  @Test
  void crossJoinFiveParametersWithOneVariations()
  {
    ArrayList<OptionsForAParameter> names = new ArrayList<>();
    names.add(new OptionsForAParameter<>(0, "Nick"));
    names.add(new OptionsForAParameter<>(1, "Howie"));
    names.add(new OptionsForAParameter<>(2, "Brian"));
    names.add(new OptionsForAParameter<>(3, "AJ"));
    names.add(new OptionsForAParameter<>(4, "Kevin"));
    // for method:
    // void everybody(String singer1, String singer2, String singer3, String singer4, String singer5)
    List<Case> cases = InParameterOrderStrategy.crossJoin(names);
    Approvals.verifyAll("CrossJoin", cases);
  }
  @Test
  void crossJoinThreeParametersWithMultipleVariations()
  {
    ArrayList<OptionsForAParameter> names = new ArrayList<>();
    names.add(new OptionsForAParameter<>(0, "MainSinger1"));
    names.add(new OptionsForAParameter<>(0, "MainSinger2"));
    names.add(new OptionsForAParameter<>(1, "BackupSinger1"));
    names.add(new OptionsForAParameter<>(1, "BackupSinger2"));
    names.add(new OptionsForAParameter<>(2, "Groupie1"));
    names.add(new OptionsForAParameter<>(2, "Groupie2"));
    names.add(new OptionsForAParameter<>(2, "Groupie3"));
    // for method:
    // void everybody(String mainSinger, String backupSinger, String groupie)
    List<Case> cases = InParameterOrderStrategy.crossJoin(names);
    Approvals.verifyAll("CrossJoin", cases);
  }
  public String toString(List<Case> result)
  {
    return result.stream().map(Case::toString).collect(Collectors.joining("\n"));
  }
}
