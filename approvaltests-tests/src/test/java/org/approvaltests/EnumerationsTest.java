package org.approvaltests;

import org.junit.jupiter.api.Test;

public class EnumerationsTest
{
  @Test
  public void testNumbers()
  {
    Approvals.verifyAll("i", new Integer[]{5, 4, 3, 2, 1});
  }

  @Test
  public void testNumbersWithHeader()
  {
    Approvals.verifyAll("The Numbers", "i", new Integer[]{5, 4, 3, 2, 1});
  }

  @Test
  public void testNumbersWithLambdas()
  {
    String[] numbers = new String[]{"one", "two", "three", "four"};
    Approvals.verifyAll(numbers, a -> a + " => " + a.length());
  }

  @Test
  public void testNumbersWithLambdasAndHeader()
  {
    String[] numbers = new String[]{"one", "two", "three", "four"};
    Approvals.verifyAll("Lengths of Strings", numbers, a -> a + " => " + a.length());
  }
}
