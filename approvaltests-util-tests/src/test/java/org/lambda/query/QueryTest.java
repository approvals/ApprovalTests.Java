package org.lambda.query;

import org.approvaltests.legacycode.Range;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
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

  @Test
  void queryVsStreamExample() {
    {
      // begin-snippet: query_example
      Integer[] numbers = Range.get(1, 20);
      Integer[] evenQueryNumbers = Query
              .where(numbers, n -> n % 2 == 0)
              .orderBy(OrderBy.Order.Descending, n -> n)
              .asArray();
      // end-snippet
      // begin-snippet: stream_example
      Integer[] evenStreamNumbers = Arrays.stream(numbers)
              .filter(n -> n % 2 == 0)
              .sorted((o1, o2) -> o2.compareTo(o1))
              .toArray(Integer[]::new);
      // end-snippet
      Assertions.assertArrayEquals(evenQueryNumbers, evenStreamNumbers);
    }

    {
      // begin-snippet: query_sum_example
      String[] names = {"Llewellyn", "Scott"};
      int lengthsFromQuery = Query.sum(names, n -> n.length()).intValue();
      // end-snippet
      // begin-snippet: stream_sum_example
      int lengthsFromStream = (int)Arrays.stream(names).map(n -> n.length()).reduce(0, (a, b) -> a + b);
      // end-snippet
      Assertions.assertEquals(lengthsFromQuery, lengthsFromStream);
    }
  }
}
