package org.lambda.query;

import com.spun.util.ClassUtils;
import com.spun.util.ObjectUtils;
import com.spun.util.StringUtils;
import org.approvaltests.Approvals;
import org.lambda.utils.Range;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
  void testTakeAndSkip()
  {
    Integer[] integers = Range.get(1, 10);
    Integer[] skip = Query.skip(integers, 3).asArray();
    Approvals.verify(Query.take(skip, 4));
  }
  @Test
  void testSkipTooMany()
  {
    Integer[] integers = Range.get(1, 3);
    List<Integer> list = Arrays.asList(integers);
    assertEquals(Query.skip(integers, 3).size(), 0);
    assertEquals(Query.skip(integers, 4).size(), 0);
    integers = null;
    assertEquals(Query.skip(integers, 3).size(), 0);
    assertEquals(Query.skip(list, 3).size(), 0);
    assertEquals(Query.skip(list, 4).size(), 0);
    list = null;
    assertEquals(Query.skip(list, 3).size(), 0);
  }
  @Test
  void testTakeTooMany()
  {
    Integer[] integers = Range.get(1, 3);
    List<Integer> list = Arrays.asList(integers);
    assertEquals(Query.take(integers, 4).size(), 3);
    integers = null;
    assertEquals(Query.take(integers, 3).size(), 0);
    assertEquals(Query.take(list, 4).size(), 3);
    list = null;
    assertEquals(Query.take(list, 3).size(), 0);
  }

  @Test
  void testMax() {
    Integer[] integers = Range.get(1, 3);
    int max = Query.max(Arrays.asList(integers), (i) -> i % 3 * 10);
    assertEquals(2, max);
    max = Query.max(integers, (i) -> i % 3 * 10);
    assertEquals(2, max);
  }

  @Test
  void testArrayAndListParity() {
    // get all methods for Query
    Queryable<Method> declaredMethods = Queryable.as(Query.class.getDeclaredMethods());
    // sort the ones that take an array
    Queryable<Method> arrays = declaredMethods.where(m -> m.getParameterTypes().length >= 1 && m.getParameterTypes()[0].isArray());
    Queryable<Method> iterables = declaredMethods.where(m -> m.getParameterTypes().length >= 1 && ObjectUtils.isThisInstanceOfThat(m.getParameterTypes()[0], Iterable.class));

    // sort the ones that take an Iterable
    // for each array that doesn't have a corresponding iterable
    //    make a note
    // for each iterable that doesn't have a corresponding array
    //   make a note
    // compare the notes
    arrays.addAll(iterables);
    Queryable<String> missingMethods = arrays.select(m -> printMethod(m)).orderBy(m -> m);
    Approvals.verifyAll("Methods without a corresponding array or list", missingMethods, m -> m);
  }

  private String printMethod(Method m) {
    return String.format("%s.%s(%s)", m.getDeclaringClass().getSimpleName(), m.getName(), showParameters(m));
  }
  private String showParameters(Method m)
  {
    return StringUtils.join(Query.select(m.getParameters(), p -> String.format("%s", p.getType().getSimpleName())),
            ",");
  }
}
