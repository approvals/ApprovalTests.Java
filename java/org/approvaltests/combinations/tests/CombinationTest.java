package org.approvaltests.combinations.tests;

import org.approvaltests.combinations.CombinationApprovals;
import org.approvaltests.combinations.SkipCombination;
import org.junit.Test;

public class CombinationTest
{
  @Test
  public void testLockDown() throws Exception
  {
    CombinationApprovals.verifyAllCombinations((i, s) -> String.format("[%s, %s]", i, s),
        new Integer[]{1, 2, 3, 4, 5}, new String[]{"a", "b", "c", "d"});
  }
  @Test
  public void testPassMethod() throws Exception
  {
    CombinationApprovals.verifyAllCombinations(this::processCall, new Integer[]{1, 2, 3, 4, 5},
        new String[]{"a", "b", "c", "d"});
  }
  public Object processCall(Integer i, String s)
  {
    if (i == 5) { throw new RuntimeException("5 is not alive"); }
    if ("d".equals(s)) { throw new SkipCombination(); }
    return String.format("[%s, %s]", i, s);
  }
  //  public void testSizes() throws Exception
  //  {
  //    Approvals.verifyAll("size", LegacyApprovals.getSizes(new String[9], new String[3], new String[5]));
  //  }
  //  public void testLockDownWith3Things() throws Exception
  //  {
  //    LegacyApprovals.LockDown(this, "processCall", AllPoints.get(0, 0, 1, 1), new String[]{"a", "b", "c", "d"},
  //        new Integer[]{1, 2, 3, 4, 5});
  //  }
  //  public Object processCall(Point p, String s, Integer i)
  //  {
  //    return String.format("[%s, %s, %s]", p, s, i);
  //  }
}
