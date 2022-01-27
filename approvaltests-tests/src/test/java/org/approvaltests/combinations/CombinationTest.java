package org.approvaltests.combinations;

import org.junit.jupiter.api.Test;

public class CombinationTest
{
  @Test
  public void testLockDown()
  {
    CombinationApprovals.verifyAllCombinations((i, s) -> String.format("[%s, %s]", i, s),
        new Integer[]{1, 2, 3, 4, 5}, new String[]{"a", "b", "c", "d"});
  }
  @Test
  public void testCombinations()
  {
    Integer[] points = new Integer[]{4, 5, 10};
    String[] words = new String[]{"Bookkeeper", "applesauce"};
    CombinationApprovals.verifyAllCombinations((i, s) -> s.substring(0, i), points, words);
  }
  @Test
  public void test1Parameter()
  {
    CombinationApprovals.verifyAllCombinations(i -> i * i, new Integer[]{1, 2, 3, 4, 5});
  }
  @Test
  public void testPassMethod()
  {
    CombinationApprovals.verifyAllCombinations(this::processCall, new Integer[]{1, 2, 3, 4, 5},
        new String[]{"a", "b", "c", "d"});
  }
  public Object processCall(Integer i, String s)
  {
    if (i == 5)
    { throw new RuntimeException("5 is not alive"); }
    if ("d".equals(s))
    { throw new SkipCombination(); }
    return String.format("[%s, %s]", i, s);
  }
  @Test
  void templateCode()
  {
    // begin-snippet: CombinationsStartingPoint
    String[] inputs1 = {"input1.value1", "input1.value2"};
    String[] inputs2 = {"input2.value1", "input2.value2", "input2.value3"};
    CombinationApprovals.verifyAllCombinations((a, b) -> "placeholder", inputs1, inputs2);
    // end-snippet
  }
  @Test
  void testCombinationsOfTwo()
  {
    // begin-snippet: YouCanVerifyCombinationsOf2
    String[] strings = {"hello", "world"};
    Integer[] numbers = {1, 2, 3};
    CombinationApprovals.verifyAllCombinations((s, i) -> String.format("(%s,%s)", s, i), strings, numbers);
    // end-snippet
  }
}
