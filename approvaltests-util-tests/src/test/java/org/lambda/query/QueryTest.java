package org.lambda.query;

import org.approvaltests.Approvals;
import org.lambda.utils.Range;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
  void queryVsStreamExample()
  {
    {
      // begin-snippet: query_example
      Integer[] numbers = Range.get(1, 20);
      Integer[] evenQueryNumbers = Query.where(numbers, n -> n % 2 == 0).orderBy(OrderBy.Order.Descending, n -> n)
          .asArray();
      // end-snippet
      // begin-snippet: stream_example
      Integer[] evenStreamNumbers = Arrays.stream(numbers).filter(n -> n % 2 == 0)
          .sorted((o1, o2) -> o2.compareTo(o1)).toArray(Integer[]::new);
      // end-snippet
      Assertions.assertArrayEquals(evenQueryNumbers, evenStreamNumbers);
    }
    {
      // begin-snippet: query_sum_example
      String[] names = {"Llewellyn", "Scott"};
      int lengthsFromQuery = Query.sum(names, n -> n.length()).intValue();
      // end-snippet
      // begin-snippet: stream_sum_example
      int lengthsFromStream = (int) Arrays.stream(names).map(n -> n.length()).reduce(0, (a, b) -> a + b);
      // end-snippet
      Assertions.assertEquals(lengthsFromQuery, lengthsFromStream);
    }
    {
      Integer[] numbers = Range.get(1, 20);
      // begin-snippet: list_is_queryable
      List<String> strings = Query.select(numbers, n -> "" + n);
      // end-snippet
      // begin-snippet: list_from_stream
      List<String> strings2 = Arrays.stream(numbers).map(n -> "" + n).collect(Collectors.toList());
      // end-snippet
    }
  }

  @Test
  void testTakeAndSkip() {
    Integer[] integers = Range.get(1, 10);
    Integer[] skip = Query.skip(integers, 3).asArray();
    Approvals.verify(Query.take(skip, 4));
  }
  @Test
  void testSkipTooMany() {
    Integer[] integers = Range.get(1, 3);
    Approvals.verify(Query.skip(integers, 4));
  }
}
