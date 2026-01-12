package org.approvaltests.combinations;

import com.spun.util.markdown.table.MarkdownColumn;
import com.spun.util.markdown.table.MarkdownTable;
import org.approvaltests.Approvals;
import org.approvaltests.combinations.pairwise.Case;
import org.approvaltests.combinations.pairwise.InParameterOrderStrategy;
import org.approvaltests.combinations.pairwise.OptionsForAParameter;
import org.approvaltests.combinations.pairwise.Pairwise;
import org.approvaltests.core.Options;
import org.approvaltests.scrubbers.RegExScrubber;
import org.junit.jupiter.api.Test;
import org.lambda.actions.Action2;
import org.lambda.utils.Range;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//@UseReporter(DiffMergeReporter.class)
public class PairWiseTest
{
  @Test
  void forTable()
  {
    String[] headers = {"Number of Parameters",
                        "Variations per Parameter",
                        "Total Combinations",
                        "Pairwise Combinations"};
    MarkdownTable table = MarkdownTable.withHeaders(headers);
    table.setColumnProperties(MarkdownColumn.RIGHT_JUSTIFIED);
    addPairwiseTableRow(2, 5, table);
    addPairwiseTableRow(3, 3, table);
    addPairwiseTableRow(3, 4, table);
    addPairwiseTableRow(4, 5, table);
    addPairwiseTableRow(5, 6, table);
    addPairwiseTableRow(9, 9, table);
    // added blank lines at beginning due to markdown snippets bug
    Approvals.verify(String.format("\n\n%s\n\n", table.toMarkdown()),
        new Options().forFile().withExtension("include.md"));
  }

  private void addPairwiseTableRow(int pCount, int variations, MarkdownTable table)
  {
    Object[] p = Range.get(1, variations);
    Pairwise pairwise = Pairwise.toPairWise(p, p, 3 <= pCount ? p : CombinationApprovals.EMPTY,
        4 <= pCount ? p : CombinationApprovals.EMPTY, 5 <= pCount ? p : CombinationApprovals.EMPTY,
        6 <= pCount ? p : CombinationApprovals.EMPTY, 7 <= pCount ? p : CombinationApprovals.EMPTY,
        8 <= pCount ? p : CombinationApprovals.EMPTY, 9 <= pCount ? p : CombinationApprovals.EMPTY);
    final List<Case> cases = pairwise.getCases();
    int totalPossibleSize = pairwise.getTotalPossibleCombinations();
    DecimalFormat df = new DecimalFormat("###,###,###");
    table.addRow(pCount, p.length, df.format(totalPossibleSize), cases.size());
  }

  @Test
  public void testPairs()
  {
    CombinationApprovals.verifyBestCoveringPairs((a, b, c, d) -> "", new Integer[]{1, 2, 3, 4, 5},
        new String[]{"a", "b", "c", "d"}, new String[]{"L", "M", "N", "O", "P"}, new Double[]{1.1, 2.2, 3.3, 4.4});
  }

  @Test
  public void testPairsWithHeader()
  {
    CombinationApprovals.verifyBestCoveringPairs(
        new Labels().withHeader("processCall(int, String, String, double)"), (a, b, c, d) -> "",
        new Integer[]{1, 2, 3, 4, 5}, new String[]{"a", "b", "c", "d"}, new String[]{"L", "M", "N", "O", "P"},
        new Double[]{1.1, 2.2, 3.3, 4.4});
  }

  @Test
  public void testPairsWithLabels()
  {
    CombinationApprovals.verifyBestCoveringPairs(
        new Labels().withLabels("number", "letter", "uppercase", "decimal"), (a, b, c, d) -> "",
        new Integer[]{1, 2, 3, 4, 5}, new String[]{"a", "b", "c", "d"}, new String[]{"L", "M", "N", "O", "P"},
        new Double[]{1.1, 2.2, 3.3, 4.4});
  }

  @Test
  public void testPairsWithHeaderAndLabels()
  {
    CombinationApprovals.verifyBestCoveringPairs(
        new Labels().withHeader("processCall(int, String, String, double)").withLabels("number", "letter",
            "uppercase", "decimal"),
        (a, b, c, d) -> "", new Integer[]{1, 2, 3, 4, 5}, new String[]{"a", "b", "c", "d"},
        new String[]{"L", "M", "N", "O", "P"}, new Double[]{1.1, 2.2, 3.3, 4.4});
  }

  @Test
  public void testPairsScrubbed()
  {
    CombinationApprovals.verifyBestCoveringPairs((a, b, c, d) -> "", new Integer[]{112, 111, 113, 114, 115},
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
    assertPairwiseCombinations(input1, input2, input3, input4);
  }

  @Test
  public void testPairPropertiesAgain()
  {
    Integer[] input1 = {112, 111};
    Integer[] input2 = {221, 222, 223, 224};
    Integer[] input3 = {331, 332, 333, 334, 335};
    Integer[] input4 = {441, 443, 444};
    Integer[] input5 = {551, 552, 553, 554, 555, 556};
    Integer[] input6 = {663, 664};
    Integer[] input7 = {771, 772, 773};
    assertPairwiseCombinations(input1, input2, input3, input4, input5, input6, input7);
  }

  private void assertPairwiseCombinations(Integer[]... inputs)
  {
    HashMap<String, Integer> pairCount = getAllPairsCount(inputs);
    assertAllPairsPresent(pairCount, inputs);
    int allPairCombinationCount = calculateAllPairCombinationCount(inputs);
    //assertEquals(121, allPairCombinationCount);
    assertEquals(allPairCombinationCount, pairCount.size());
  }

  public int calculateAllPairCombinationCount(Integer[]... inputs)
  {
    int[] allPairCombinationCount = {0};
    actOnAllPairs(inputs, (i1, i2) -> allPairCombinationCount[0] += i1.length * i2.length);
    return allPairCombinationCount[0];
  }

  public void assertAllPairsPresent(HashMap<String, Integer> pairCount, Integer[]... inputs)
  {
    actOnAllPairs(inputs, (i1, i2) -> assertAllPairsPresent(i1, i2, pairCount));
  }

  public void actOnAllPairs(Integer[][] inputs, Action2<Integer[], Integer[]> action)
  {
    for (int i1 = 0; i1 < inputs.length - 1; i1++)
    {
      for (int i2 = i1 + 1; i2 < inputs.length; i2++)
      {
        action.call(inputs[i1], inputs[i2]);
      }
    }
  }

  public HashMap<String, Integer> getAllPairsCount(Integer[]... inputs)
  {
    Pairwise pairwise = Pairwise.toPairWise(inputs);
    final List<Case> cases = pairwise.getCases();
    HashMap<String, Integer> pairCount = new HashMap<>();
    for (Case params : cases)
    {
      for (int i1 = 0; i1 < inputs.length - 1; i1++)
      {
        for (int i2 = i1 + 1; i2 < inputs.length; i2++)
        {
          final Integer in1 = (Integer) params.get(i1);
          final Integer in2 = (Integer) params.get(i2);
          addPair(in1, in2, pairCount);
        }
      }
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
  void testHorizontalGrowth()
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
  void testGeneratePairs()
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

  List<List<Case>> generatePairs(Object[]... parameters)
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
