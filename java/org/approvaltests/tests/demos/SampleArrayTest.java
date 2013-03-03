package org.approvaltests.tests.demos;

import java.util.Arrays;

import junit.framework.TestCase;

import org.approvaltests.Approvals;

public class SampleArrayTest extends TestCase
{
  public void testList() throws Exception
  {
    String[] names = {"Llewellyn", "James", "Dan", "Jason", "Katrina"};
    Arrays.sort(names);
    Approvals.verifyAll("", names);
  }
}
