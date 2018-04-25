package org.approvaltests.legacycode.tests;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.approvaltests.legacycode.IndexPermutations;

public class IndexPermutationsTest extends TestCase
{
  public void testSizes() throws Exception
  {
    Approvals.verifyAll("type", new IndexPermutations(new Integer[]{3, 4, 1, 2}));
  }
  public void ptest128Equals128() throws Exception
  {
    Integer[] i1 = {127, 128};
    Integer[] i2 = {127, 128};
    assertTrue("127", i1[0] == i2[0]);
    assertTrue("128", 128 == i2[1]);
    assertTrue("128", i1[1] == i2[1]);
  }
}
