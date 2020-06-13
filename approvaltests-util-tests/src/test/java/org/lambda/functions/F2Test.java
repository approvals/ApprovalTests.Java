package org.lambda.functions;

import java.awt.Point;
import java.util.HashMap;
import org.approvaltests.Approvals;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.windows.TortoiseTextDiffReporter;
import org.junit.jupiter.api.Test;
import org.lambda.utils.Grid;

@UseReporter(TortoiseTextDiffReporter.class)
public class F2Test
{
  @Test
  public void testSquare() throws Exception
  {
    final HashMap<Point, String> map = new HashMap<Point, String>();
    map.put(new Point(3, 4), "K");
    map.put(new Point(4, 4), "Q");
    map.put(new Point(1, 1), "P");
    String out = Grid.print(6, 5, (a, b) -> (map.get(new Point(a, b))));
    Approvals.verify(out);
  }
  @Test
  public void testDiagonal() throws Exception
  {
    String out = Grid.print(6, 5, (a, b) -> (a == b ? "X" : "."));
    Approvals.verify(out);
  }
}
