package org.lambda.functions.tests;

import java.awt.Point;
import java.util.HashMap;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.windows.TortoiseTextDiffReporter;
import org.lambda.functions.implementations.F2;
import org.lambda.utils.Grid;

@UseReporter(TortoiseTextDiffReporter.class)
public class F2Test extends TestCase
{
  public void testSquare() throws Exception
  {
    final HashMap<Point, String> map = new HashMap<Point, String>();
    map.put(new Point(3, 4), "K");
    map.put(new Point(4, 4), "Q");
    map.put(new Point(1, 1), "P");
    String out = Grid.print(6, 5, new F2<Integer, Integer, String>(0, 1,map){{returnValue(map.get(new Point(a, b)));}});
    Approvals.verify(out);
  }
  public void testDiagonal() throws Exception
  {
    String out = Grid.print(6, 5, new F2<Integer, Integer, String>(0, 1)
    {
      {
        returnValue(a == b ? "X" : ".");
      }
    });
    Approvals.verify(out);
  }
}
