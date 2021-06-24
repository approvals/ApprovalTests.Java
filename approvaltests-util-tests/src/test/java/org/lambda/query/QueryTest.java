package org.lambda.query;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryTest
{
  @Test
  public void testQueryAll() throws Exception
  {
    List<Integer> numbers = Arrays.asList(3, 5, 7, 9);
    assertEquals(true, Query.all(numbers, n -> 3 <= n));
    assertEquals(false, Query.all(numbers, n -> 4 <= n));
  }
  @Test
  public void testQueryAllOfNothing() throws Exception
  {
    // None of the cases return false for empty data sets
    List<Integer> numbers = Arrays.asList();
    assertEquals(true, Query.all(numbers, n -> 3 <= n));
  }
}
