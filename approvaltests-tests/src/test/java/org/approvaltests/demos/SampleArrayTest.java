package org.approvaltests.demos;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

// begin-snippet: demo
public class SampleArrayTest
{
  @Test
  public void testList()
  {
    String[] names = {"Llewellyn", "James", "Dan", "Jason", "Katrina"};
    Arrays.sort(names);
    Approvals.verifyAll("", names);
  }
}
// end-snippet
