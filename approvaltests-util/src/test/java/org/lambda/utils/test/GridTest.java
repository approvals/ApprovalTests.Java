package org.lambda.utils.test;

import org.approvaltests.Approvals;
import org.junit.Test;
import org.lambda.utils.Grid;

public class GridTest
{
  @Test
  public void testSimple()
  {
    Approvals.verify(Grid.print(2, 3, "."));
  }
  @Test
  public void testMultiply()
  {
    Approvals.verify(Grid.print(2, 3, (x, y) -> "" + ((x + 1) * (y + 1))));
  }
}
