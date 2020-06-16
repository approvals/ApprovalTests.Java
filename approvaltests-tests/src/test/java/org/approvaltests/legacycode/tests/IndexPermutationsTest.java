package org.approvaltests.legacycode.tests;

import org.approvaltests.Approvals;
import org.approvaltests.legacycode.IndexPermutations;
import org.junit.jupiter.api.Test;

public class IndexPermutationsTest
{
  @Test
  public void testSizes()
  {
    Approvals.verifyAll("type", new IndexPermutations(new Integer[]{3, 4, 1, 2}));
  }
}
