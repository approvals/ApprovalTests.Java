package org.approvaltests.combinations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.approvaltests.Approvals;
import org.approvaltests.combinations.pairwise.Case;
import org.approvaltests.combinations.pairwise.InParameterOrderStrategy;
import org.approvaltests.combinations.pairwise.OptionsForAParameter;
import org.approvaltests.combinations.pairwise.Pairwise;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.macosx.DiffMergeReporter;
import org.approvaltests.scrubbers.RegExScrubber;
import org.junit.jupiter.api.Test;

@UseReporter(DiffMergeReporter.class)
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
  public void testPairProperties()
  {
    Integer[] input1 = {112, 111, 113, 114, 115};
    Integer[] input2 = {221, 222, 223, 224};
    Integer[] input3 = {331, 332, 333, 334, 335};
    Integer[] input4 = {441, 442, 443, 444};
    HashMap<String, Integer> pairCount = getAllPairsCount(input1, input2, input3, input4);
    assertAllPairsPresent(input1, input2, pairCount);
    assertAllPairsPresent(input1, input3, pairCount);
    assertAllPairsPresent(input1, input4, pairCount);
    assertAllPairsPresent(input2, input3, pairCount);
    assertAllPairsPresent(input2, input4, pairCount);
    assertAllPairsPresent(input3, input4, pairCount);
    int allPairCombinationCount = input1.length * input2.length + input1.length * input3.length
        + input1.length * input4.length + input2.length * input3.length + input2.length * input4.length
        + input3.length * input4.length;
    assertEquals(121, allPairCombinationCount);
    assertEquals(allPairCombinationCount, pairCount.size());
  }

  public HashMap<String, Integer> getAllPairsCount(Integer[] input1, Integer[] input2, Integer[] input3, Integer[] input4) {
    Pairwise pairwise = PairWiseApprovals.toPairWise(input1, input2, input3, input4);
    final List<Case> cases = pairwise.getCases();
    HashMap<String, Integer> pairCount = new HashMap<>();
    for (Case params : cases)
    {
      final Integer in1 = (Integer) params.get(0);
      final Integer in2 = (Integer) params.get(1);
      final Integer in3 = (Integer) params.get(2);
      final Integer in4 = (Integer) params.get(3);
      addPair(in1, in2, pairCount);
      addPair(in1, in3, pairCount);
      addPair(in1, in4, pairCount);
      addPair(in2, in3, pairCount);
      addPair(in2, in4, pairCount);
      addPair(in3, in4, pairCount);
    }
    return pairCount;
  }

  public void assertAllPairsPresent(Integer[] input1, Integer[] input3, HashMap<String, Integer> pairCount)
  {
    for (Integer i1 : input1)
    {
      for (Integer i2 : input3)
      {
        String key = getKey(i1, i2);
        if (!pairCount.containsKey(key))
        {
          fail("missing pair " + key);
        }
      }
    }
  }
  public String getKey(Integer i1, Integer i2)
  {
    return i1 + "," + i2;
  }
  private void addPair(Integer in1, Integer in2, HashMap<String, Integer> pairCount)
  {
    String key = getKey(in1, in2);
    Integer count = pairCount.computeIfAbsent(key, __ -> 0) + 1;
    pairCount.put(key, count);
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
