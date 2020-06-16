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
}
