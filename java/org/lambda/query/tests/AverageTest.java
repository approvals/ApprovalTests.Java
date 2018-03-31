package org.lambda.query.tests;

import java.util.Arrays;
import java.util.List;

import org.lambda.query.Query;

import junit.framework.TestCase;

public class AverageTest extends TestCase
{
  public void testAverage() throws Exception
  {
    List<Integer> numbers = Arrays.asList(3, 5, 7, 9);
    assertEquals(6, Query.average(numbers, a -> a), 0.00);
  }
  public void testSum() throws Exception
  {
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
    assertEquals(5, Query.sum(numbers, a -> (a % 2)), 0.00);
    assertEquals(45, Query.sum(numbers), 0.00);
  }
  public void testMaxAndMin() throws Exception
  {
    List<Integer> numbers = Arrays.asList(40, 20, 170, 30);
    assertEquals(170, (int) Query.max(numbers));
    assertEquals(20, (int) Query.min(numbers));
  }
}
