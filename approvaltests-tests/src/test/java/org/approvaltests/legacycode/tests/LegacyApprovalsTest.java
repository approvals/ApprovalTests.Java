package org.approvaltests.legacycode.tests;

import org.approvaltests.Approvals;
import org.approvaltests.legacycode.AllPoints;
import org.approvaltests.legacycode.LegacyApprovals;
import org.junit.jupiter.api.Test;

import java.awt.Point;

public class LegacyApprovalsTest
{
  @Test
  public void testLockDown()
  {
    LegacyApprovals.LockDown(this, "processCall", new Integer[]{1, 2, 3, 4, 5}, new String[]{"a", "b", "c", "d"});
  }

  public Object processCall(Integer i, String s)
  {
    return String.format("[%s, %s]", i, s);
  }

  @Test
  public void testSizes()
  {
    Approvals.verifyAll("size", LegacyApprovals.getSizes(new String[9], new String[3], new String[5]));
  }

  @Test
  public void testLockDownWith3Things()
  {
    LegacyApprovals.LockDown(this, "processCall", AllPoints.get(0, 0, 1, 1), new String[]{"a", "b", "c", "d"},
        new Integer[]{1, 2, 3, 4, 5});
  }

  public Object processCall(Point p, String s, Integer i)
  {
    return String.format("[%s, %s, %s]", p, s, i);
  }
}
