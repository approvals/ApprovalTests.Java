package org.lambda.query;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;
import org.lambda.Extendable;
import org.lambda.utils.Range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryableTest
{
  // begin-snippet: implementing-extendable
  public static class CustomQuery implements Extendable<List<String>>
  {
    private List<String> caller;
    @Override
    public void setCaller(List<String> caller)
    {
      this.caller = caller;
    }
    // end-snippet
    // begin-snippet: extendable-query
    public Queryable<String> findFirstWordsOnly()
    {
      return findFirstWordsOnly(caller);
    }
    // end-snippet
    // begin-snippet: custom-query
    public static Queryable<String> findFirstWordsOnly(List<String> words)
    {
      return Query.select(words, w -> {
        int i = w.indexOf(' ');
        if (i == -1)
        {
          return w;
        }
        else
        {
          return w.substring(0, i);
        }
      });
    }
    // end-snippet
  }
  @Test
  void example()
  {
    // begin-snippet: custom-query-example
    Queryable<String> list = Queryable.as("One fish", "two fish", "red fish", "blue fish");
    Queryable<String> firstWordsOnlyWithExtension = list.select(String::toUpperCase).use(CustomQuery.class)
        .findFirstWordsOnly();
    // end-snippet
    // begin-snippet: custom-query-example-static
    Queryable<String> firstWordsOnlyStatic = CustomQuery
        .findFirstWordsOnly(Query.select(list, String::toUpperCase));
    // end-snippet
    assertArrayEquals(firstWordsOnlyStatic.toArray(), firstWordsOnlyWithExtension.toArray());
    Approvals.verifyAll("firstWordsOnly", firstWordsOnlyWithExtension);
  }
  @Test
  void testDistinct()
  {
    Queryable<Integer> distinct = Queryable.as(3, 2, 1, 1, 3).distinct();
    Approvals.verifyAll("", distinct);
  }
  @Test
  void testToArray()
  {
    Character[] letters = Queryable.as('L', 'a', 'r', 's').asArray();
    Approvals.verifyAll("", letters);
  }
  @Test
  void testEmptyArrays()
  {
    String[] strings = new String[0];
    Queryable<String> as = Queryable.as(strings);
    as.add("Lars");
    String[] asArray = as.asArray();
    Approvals.verifyAll("", asArray);
  }
  @Test
  void testEmptyList()
  {
    List<String> strings = new ArrayList<>();
    Queryable<String> as = Queryable.as(strings, String.class); // Queryable.as(strings); ?
    as.add("Lars");
    String[] asArray = as.asArray();
    Approvals.verifyAll("", asArray);
  }
  @Test
  void testSuperClassCommonality()
  {
    List<Number> numbers = new ArrayList<>();
    numbers.add(1);
    numbers.add(3.1415);
    Queryable<Number> as = Queryable.as(numbers); // Queryable.as(numbers); ?
    as.add(2.4);
    Number[] asArray = as.asArray();
    Approvals.verifyAll("", asArray);
  }
  @Test
  void testInterfaceCommonality()
  {
    List<Comparable> comparables = new ArrayList<>();
    comparables.add(null);
    comparables.add(1);
    comparables.add(3.1415);
    comparables.add("Lars");
    Queryable<Comparable> queryable = Queryable.as(comparables);
    assertEquals(Comparable.class, queryable.getType());
    queryable.add(2.4);
    Comparable[] asArray = queryable.asArray();
    Approvals.verifyAll("", asArray);
    Temp<String> t = new Temp(Integer.class, "HELLO");
  }
  public static class Temp<H>
  {
    private final Class<H> c;
    public Temp(Class<H> c, H h)
    {
      this.c = c;
    }
  }
  @Test
  void testSelectMany()
  {
    Queryable<List<String>> names = Queryable.as(Arrays.asList("Anna", "Abby", "Alice"),
        Arrays.asList("Bob", "Barbara", "Barry"));
    Queryable<String> allNames = names.selectMany(n -> n);
    Approvals.verifyAll("", allNames);
  }
  @Test
  void testSelectManyCharacters()
  {
    // begin-snippet: queryable_select_many
    Queryable<String> names = Queryable.as("Now is the time", "Fourscore and seven years ago",
        "When in the course of human events");
    Queryable<String> allNames = names.selectMany(n -> Arrays.asList(n.split(" "))).orderBy(n -> n);
    // end-snippet
    Approvals.verifyAll("", allNames);
  }
  @Test
  void testGroupBy()
  {
    // begin-snippet: group_by_key
    Queryable<String> words = Queryable.as("Jack", "and", "Jill", "jumped", "up", "the", "hill");
    Queryable<Entry<Character, Queryable<String>>> result = words.groupBy(w -> w.toLowerCase().charAt(0));
    // end-snippet
    Approvals.verifyAll("", result);
  }
  @Test
  void testGroupByWordLength()
  {
    Queryable<String> words = Queryable.as("One Fish Two Fish Red Fish Blue Fish".split(" "));
    Queryable<Entry<Object, Queryable<String>>> result = words.groupBy(w -> w.length());
    Approvals.verifyAll("", result, r -> String.format("%s = %s", r.getKey(), r.getValue()));
  }
  @Test
  void testGroupByWordCount()
  {
    Queryable<String> words = Queryable.as("One Fish Two Fish Red Fish Blue Fish".split(" "));
    Queryable<Entry<String, Integer>> result = words.groupBy(w -> w, w -> w, r -> r.size());
    Approvals.verifyAll("", result, r -> String.format("%s = %s", r.getKey(), r.getValue()));
  }
  @Test
  void testGroupByCombineWordsOfSimilarLengths()
  {
    // begin-snippet: group_by_full
    Queryable<String> words = Queryable.as("One Fish Two Fish Red Fish Blue Fish".split(" "));
    Queryable<Entry<Object, Object>> result = words.groupBy(w -> w.length(), w -> w.toLowerCase(),
        r -> r.join("_"));
    // end-snippet
    Approvals.verifyAll("", result, r -> String.format("%s = %s", r.getKey(), r.getValue()));
  }
  @Test
  void testJoin()
  {
    String result = Queryable.as("hello", null, "world").join("_");
    assertEquals("hello_null_world", result);
  }
  @Test
  void testJoinWithTransformation()
  {
    String result = Queryable.as("Hello", "World").join("_", String::toUpperCase);
    assertEquals("HELLO_WORLD", result);
  }
  @Test
  void testTakeAndSkip()
  {
    Integer[] integers = Range.get(1, 10);
    Approvals.verify(Queryable.as(integers).skip(3).take(4));
  }
  @Test
  void testCreateEmpty()
  {
    Queryable<Integer> empty = Queryable.createEmpty(null);
    assertEquals(Object.class, empty.getType());
    empty = Queryable.createEmpty(new Integer[]{});
    assertEquals(Integer.class, empty.getType());
    empty = Queryable.createEmpty(new Integer[]{1});
    assertEquals(Integer.class, empty.getType());
  }
  @Test
  void testRecursive()
  {
    Queryable<Integer> integers = Queryable.as(48, 8);
    Approvals.verifyAll("", integers.selectRecursivelyUntil(i -> i / 2, i -> i <= 1));
  }
  @Test
  void testOf()
  {
    Approvals.settings().allowMultipleVerifyCallsForThisMethod();
    verifyQueryable(Queryable.of(1, 2, 3));
    verifyQueryable(Queryable.of(Set.of(1, 2, 3)));
    verifyQueryable(Queryable.as(Set.of(1, 2, 3)));
    verifyQueryable(Queryable.of(Set.of(1, 2, 3), Integer.class));
    verifyQueryable(Queryable.as(Set.of(1, 2, 3), Integer.class));
  }
  private static void verifyQueryable(Queryable<Integer> queryable)
  {
    Approvals.verifyAll("", queryable.orderBy(i -> i));
  }
  @Test
  void testFirstOrThrow()
  {
    var expected = """
        java.lang.RuntimeException: 4 not found
        """;
    Queryable<Integer> queryable = Queryable.as(1, 2, 3);
    assertEquals(3, queryable.first(i -> 2 < i));
    Approvals.verifyException(() -> queryable.firstOrThrow(i -> i == 4, () -> new RuntimeException("4 not found")),
        new Options().inline(expected));
  }
}
