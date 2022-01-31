package org.approvaltests.legacycode.tests;

import org.approvaltests.Approvals;
import org.approvaltests.legacycode.AllPoints;
import org.junit.jupiter.api.Test;

import java.awt.Point;

public class AllPointsTest
{
  @Test
  public void testAllPoints()
  {
    Point[] p = AllPoints.get(1, 2, 9, 10);
    Approvals.verifyAll("point", p);
  }
}
