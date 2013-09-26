package org.approvaltests.legacycode.tests;

import java.awt.Point;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.approvaltests.legacycode.AllPoints;

public class AllPointsTest extends TestCase
{
  public void testAllPoints() throws Exception
  {
    Point[] p = AllPoints.get(1, 2, 9, 10);
    Approvals.verifyAll("point", p);
  }
}
