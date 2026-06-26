package org.lambda.query;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;
import org.lambda.Extendable;
import org.lambda.utils.Range;
import org.lambda.query.OrderBy.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    assertEquals(3, queryable.firstOrThrow(i -> 2 < i, () -> new RuntimeException("Nothing bigger than 2")));
    Approvals.verifyException(() -> queryable.firstOrThrow(i -> i == 4, () -> new RuntimeException("4 not found")),
        new Options().inline(expected));
  }

  @Test
  void testDefaultConstructor()
  {
    Queryable<String> q = new Queryable<>();
    assertEquals(0, q.size());
  }

  @Test
  void testConstructorWithType()
  {
    Queryable<Integer> q = new Queryable<>(Integer.class);
    assertEquals(Integer.class, q.getType());
  }

  @Test
  void testCreateEmptyWithNull()
  {
    Queryable<Integer> empty = Queryable.createEmpty(null);
    assertEquals(Object.class, empty.getType());
    assertEquals(0, empty.size());
  }

  @Test
  void testCreateEmptyWithArray()
  {
    Queryable<Integer> empty = Queryable.createEmpty(new Integer[]{});
    assertEquals(Integer.class, empty.getType());
    assertEquals(0, empty.size());
  }

  @Test
  void testCreateEmptyWithNonEmptyArray()
  {
    Queryable<Integer> empty = Queryable.createEmpty(new Integer[]{1, 2, 3});
    assertEquals(Integer.class, empty.getType());
    assertEquals(0, empty.size());
  }

  @Test
  void testOfVarargs()
  {
    Queryable<Integer> q = Queryable.of(1, 2, 3);
    assertEquals(3, q.size());
    assertEquals(1, q.get(0).intValue());
    assertEquals(2, q.get(1).intValue());
    assertEquals(3, q.get(2).intValue());
  }

  @Test
  void testOfList()
  {
    List<String> list = Arrays.asList("a", "b", "c");
    Queryable<String> q = Queryable.of(list);
    assertEquals(3, q.size());
    assertEquals("a", q.get(0));
  }

  @Test
  void testOfListWithType()
  {
    List<Number> list = Arrays.asList(1, 2.5, 3);
    Queryable<Number> q = Queryable.of(list, Number.class);
    assertEquals(3, q.size());
    assertEquals(Number.class, q.getType());
  }

  @Test
  void testOfSet()
  {
    Set<Integer> set = new HashSet<>(Arrays.asList(1, 2, 3));
    Queryable<Integer> q = Queryable.of(set);
    assertEquals(3, q.size());
  }

  @Test
  void testOfSetWithType()
  {
    Set<Number> set = new HashSet<>(Arrays.asList(1, 2.5, 3));
    Queryable<Number> q = Queryable.of(set, Number.class);
    assertEquals(3, q.size());
    assertEquals(Number.class, q.getType());
  }

  @Test
  void testOfStream()
  {
    Queryable<Integer> q = Queryable.of(Stream.of(1, 2, 3));
    assertEquals(3, q.size());
  }

  @Test
  void testAsList()
  {
    List<String> list = Arrays.asList("x", "y");
    Queryable<String> q = Queryable.as(list);
    assertEquals(2, q.size());
  }

  @Test
  void testAsListAlreadyQueryable()
  {
    Queryable<String> original = Queryable.as("a", "b");
    Queryable<String> result = Queryable.as(original);
    assertSameOrEqual(original, result);
  }

  @Test
  void testAsSet()
  {
    Set<String> set = new HashSet<>(Arrays.asList("x", "y"));
    Queryable<String> q = Queryable.as(set);
    assertEquals(2, q.size());
  }

  @Test
  void testAsSetWithType()
  {
    Set<Number> set = new HashSet<>(Arrays.asList(1, 2.5));
    Queryable<Number> q = Queryable.as(set, Number.class);
    assertEquals(2, q.size());
    assertEquals(Number.class, q.getType());
  }

  @Test
  void testAsArray()
  {
    Queryable<String> q = Queryable.as("a", "b", "c");
    assertEquals(3, q.size());
  }

  @Test
  void testAsStream()
  {
    Queryable<Integer> q = Queryable.as(Stream.of(10, 20, 30));
    assertEquals(3, q.size());
  }

  @Test
  void testSelect()
  {
    Queryable<Integer> q = Queryable.as(1, 2, 3);
    Queryable<String> result = q.select(i -> "num-" + i);
    assertEquals(3, result.size());
    assertEquals("num-1", result.get(0));
    assertEquals("num-2", result.get(1));
    assertEquals("num-3", result.get(2));
  }

  @Test
  void testSelectEmpty()
  {
    Queryable<Integer> q = new Queryable<>();
    Queryable<String> result = q.select(i -> "num-" + i);
    assertEquals(0, result.size());
  }

  @Test
  void testWhere()
  {
    Queryable<Integer> q = Queryable.as(1, 2, 3, 4, 5, 6);
    Queryable<Integer> result = q.where(i -> i > 3);
    assertEquals(3, result.size());
    assertEquals(4, result.get(0).intValue());
    assertEquals(5, result.get(1).intValue());
    assertEquals(6, result.get(2).intValue());
  }

  @Test
  void testWhereNoneMatch()
  {
    Queryable<Integer> q = Queryable.as(1, 2, 3);
    Queryable<Integer> result = q.where(i -> i > 100);
    assertEquals(0, result.size());
  }

  @Test
  void testWhereAllMatch()
  {
    Queryable<Integer> q = Queryable.as(1, 2, 3);
    Queryable<Integer> result = q.where(i -> i > 0);
    assertEquals(3, result.size());
  }

  @Test
  void testFirst()
  {
    Queryable<Integer> q = Queryable.as(10, 20, 30);
    assertEquals(10, q.first().intValue());
  }

  @Test
  void testFirstWithFilter()
  {
    Queryable<Integer> q = Queryable.as(1, 2, 3, 4, 5);
    assertEquals(3, q.first(i -> i > 2).intValue());
  }

  @Test
  void testFirstWithFilterNoMatch()
  {
    Queryable<Integer> q = Queryable.as(1, 2, 3);
    Integer result = q.first(i -> i > 100);
    assertNull(result);
  }

  @Test
  void testFirstOnEmpty()
  {
    Queryable<Integer> q = new Queryable<>();
    Integer result = q.first();
    assertNull(result);
  }

  @Test
  void testFirstOrDefaultWithDefault()
  {
    Queryable<Integer> q = new Queryable<>();
    assertEquals(42, q.firstOrDefault(42).intValue());
  }

  @Test
  void testFirstOrDefaultWithElements()
  {
    Queryable<Integer> q = Queryable.as(10, 20);
    assertEquals(10, q.firstOrDefault(42).intValue());
  }

  @Test
  void testFirstOrThrowFinds()
  {
    Queryable<Integer> q = Queryable.as(1, 2, 3);
    assertEquals(3, q.firstOrThrow(i -> i > 2, () -> new RuntimeException("not found")).intValue());
  }

  @Test
  void testFirstOrThrowThrows()
  {
    Queryable<Integer> q = Queryable.as(1, 2, 3);
    RuntimeException ex = assertThrows(RuntimeException.class,
        () -> q.firstOrThrow(i -> i > 100, () -> new RuntimeException("not found")));
    assertEquals("not found", ex.getMessage());
  }

  @Test
  void testLast()
  {
    Queryable<Integer> q = Queryable.as(10, 20, 30);
    assertEquals(30, q.last().intValue());
  }

  @Test
  void testLastSingleElement()
  {
    Queryable<Integer> q = Queryable.as(42);
    assertEquals(42, q.last().intValue());
  }

  @Test
  void testAllTrue()
  {
    Queryable<Integer> q = Queryable.as(2, 4, 6);
    assertTrue(q.all(i -> i % 2 == 0));
  }

  @Test
  void testAllFalse()
  {
    Queryable<Integer> q = Queryable.as(2, 3, 6);
    assertFalse(q.all(i -> i % 2 == 0));
  }

  @Test
  void testAllEmpty()
  {
    Queryable<Integer> q = new Queryable<>();
    assertTrue(q.all(i -> true));
  }

  @Test
  void testAnyTrue()
  {
    Queryable<Integer> q = Queryable.as(1, 2, 3);
    assertTrue(q.any(i -> i > 2));
  }

  @Test
  void testAnyFalse()
  {
    Queryable<Integer> q = Queryable.as(1, 2, 3);
    assertFalse(q.any(i -> i > 100));
  }

  @Test
  void testAnyEmpty()
  {
    Queryable<Integer> q = new Queryable<>();
    assertFalse(q.any(i -> true));
  }

  @Test
  void testMaxWithSelector()
  {
    Queryable<String> q = Queryable.as("hi", "hello", "hey");
    String result = q.max(s -> s.length());
    assertEquals("hello", result);
  }

  @Test
  void testMinWithSelector()
  {
    Queryable<String> q = Queryable.as("hi", "hello", "hey");
    String result = q.min(s -> s.length());
    assertEquals("hi", result);
  }

  @Test
  void testMaxDirect()
  {
    Queryable<Integer> q = Queryable.as(3, 1, 4, 1, 5, 9);
    assertEquals(9, q.max().intValue());
  }

  @Test
  void testMinDirect()
  {
    Queryable<Integer> q = Queryable.as(3, 1, 4, 1, 5, 9);
    assertEquals(1, q.min().intValue());
  }

  @Test
  void testAverage()
  {
    Queryable<Integer> q = Queryable.as(1, 2, 3, 4, 5);
    assertEquals(3.0, q.average(i -> i), 0.001);
  }

  @Test
  void testSumWithSelector()
  {
    Queryable<String> q = Queryable.as("1", "2", "3");
    assertEquals(6.0, q.sum(s -> Integer.parseInt(s)), 0.001);
  }

  @Test
  void testSumDirect()
  {
    Queryable<Integer> q = Queryable.as(1, 2, 3, 4);
    assertEquals(10.0, q.sum(), 0.001);
  }

  @Test
  void testOrderByAscending()
  {
    Queryable<Integer> q = Queryable.as(3, 1, 2);
    Queryable<Integer> result = q.orderBy(i -> i);
    assertEquals(1, result.get(0).intValue());
    assertEquals(2, result.get(1).intValue());
    assertEquals(3, result.get(2).intValue());
  }

  @Test
  void testOrderByDescending()
  {
    Queryable<Integer> q = Queryable.as(3, 1, 2);
    Queryable<Integer> result = q.orderBy(Order.Descending, i -> i);
    assertEquals(3, result.get(0).intValue());
    assertEquals(2, result.get(1).intValue());
    assertEquals(1, result.get(2).intValue());
  }

  @Test
  void testDistinctEmpty()
  {
    Queryable<Integer> q = new Queryable<>();
    Queryable<Integer> result = q.distinct();
    assertEquals(0, result.size());
  }

  @Test
  void testAsArrayBasic()
  {
    Character[] letters = Queryable.as('L', 'a', 'r', 's').asArray();
    assertArrayEquals(new Character[]{'L', 'a', 'r', 's'}, letters);
  }

  @Test
  void testAsArrayFromEmpty()
  {
    Queryable<String> q = Queryable.as(new String[0]);
    q.add("Lars");
    String[] arr = q.asArray();
    assertArrayEquals(new String[]{"Lars"}, arr);
  }

  @Test
  void testSelectManyArray()
  {
    Queryable<String> q = Queryable.as("ab", "cd");
    Queryable<Character> result = q
        .selectManyArray(s -> s.chars().mapToObj(c -> (char) c).toArray(Character[]::new));
    assertEquals(4, result.size());
    assertEquals('a', result.get(0));
    assertEquals('b', result.get(1));
    assertEquals('c', result.get(2));
    assertEquals('d', result.get(3));
  }

  @Test
  void testJoinEmpty()
  {
    String result = Queryable.as(new String[0]).join(",");
    assertEquals("", result);
  }

  @Test
  void testSplit()
  {
    Queryable<Integer> q = Queryable.as(1, 2, 0, 3, 4, 0, 5);
    Queryable<Queryable<Integer>> result = q.split(i -> i == 0);
    // split at 0: [1,2], [3,4], trailing [5] is NOT added since no trailing split point
    // Actually: when split point hit, add current part and reset. After loop, trailing part is NOT added.
    assertEquals(2, result.size());
    assertEquals(2, result.get(0).size());
    assertEquals(1, result.get(0).get(0).intValue());
    assertEquals(2, result.get(0).get(1).intValue());
    assertEquals(2, result.get(1).size());
    assertEquals(3, result.get(1).get(0).intValue());
    assertEquals(4, result.get(1).get(1).intValue());
  }

  @Test
  void testSplitNoSplitPoints()
  {
    Queryable<Integer> q = Queryable.as(1, 2, 3);
    Queryable<Queryable<Integer>> result = q.split(i -> i == 0);
    assertEquals(0, result.size());
  }

  @Test
  void testSplitAllSplitPoints()
  {
    Queryable<Integer> q = Queryable.as(0, 0, 0);
    Queryable<Queryable<Integer>> result = q.split(i -> i == 0);
    assertEquals(3, result.size());
    for (Queryable<Integer> part : result)
    {
      assertEquals(0, part.size());
    }
  }

  @Test
  void testSplitEmpty()
  {
    Queryable<Integer> q = new Queryable<>();
    Queryable<Queryable<Integer>> result = q.split(i -> true);
    assertEquals(0, result.size());
  }

  @Test
  void testSkip()
  {
    Queryable<Integer> q = Queryable.as(1, 2, 3, 4, 5);
    Queryable<Integer> result = q.skip(2);
    assertEquals(3, result.size());
    assertEquals(3, result.get(0).intValue());
  }

  @Test
  void testSkipMoreThanSize()
  {
    Queryable<Integer> q = Queryable.as(1, 2, 3);
    Queryable<Integer> result = q.skip(10);
    assertEquals(0, result.size());
  }

  @Test
  void testTake()
  {
    Queryable<Integer> q = Queryable.as(1, 2, 3, 4, 5);
    Queryable<Integer> result = q.take(3);
    assertEquals(3, result.size());
    assertEquals(1, result.get(0).intValue());
  }

  @Test
  void testTakeMoreThanSize()
  {
    Queryable<Integer> q = Queryable.as(1, 2, 3);
    Queryable<Integer> result = q.take(10);
    assertEquals(3, result.size());
  }

  @Test
  void testTakeAndSkipCombined()
  {
    Queryable<Integer> q = Queryable.as(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    Queryable<Integer> result = q.skip(3).take(4);
    assertEquals(4, result.size());
    assertEquals(4, result.get(0).intValue());
    assertEquals(7, result.get(3).intValue());
  }

  @Test
  void testCombine()
  {
    Queryable<Integer> q1 = Queryable.as(1, 2);
    Queryable<Integer> q2 = Queryable.as(3, 4);
    Queryable<Integer> result = q1.combine(q2);
    assertEquals(4, result.size());
    assertEquals(1, result.get(0).intValue());
    assertEquals(4, result.get(3).intValue());
  }

  @Test
  void testSelectRecursivelyUntil()
  {
    Queryable<Integer> q = Queryable.as(48, 8);
    Queryable<Integer> result = q.selectRecursivelyUntil(i -> i / 2, i -> i <= 1);
    // 48: do{add 48; item=24}while(24<=1?no) -> do{add 24; item=12}while(12<=1?no) -> do{add 12; item=6}while(6<=1?no) -> do{add 6; item=3}while(3<=1?no) -> do{add 3; item=1}while(1<=1?yes,stop) = 5 items
    // 8: do{add 8; item=4}while(4<=1?no) -> do{add 4; item=2}while(2<=1?no) -> do{add 2; item=1}while(1<=1?yes,stop) = 3 items
    // Total: 8 items
    assertEquals(8, result.size());
    assertEquals(48, result.get(0).intValue());
    assertEquals(24, result.get(1).intValue());
    assertEquals(3, result.get(4).intValue());
    assertEquals(8, result.get(5).intValue());
    assertEquals(2, result.get(7).intValue());
  }

  @Test
  void testSelectRecursivelyUntilSingleElement()
  {
    Queryable<Integer> q = Queryable.as(16);
    Queryable<Integer> result = q.selectRecursivelyUntil(i -> i / 2, i -> i <= 1);
    // 16: do{add 16; item=8}while(8<=1?no) -> do{add 8; item=4}while(4<=1?no) -> do{add 4; item=2}while(2<=1?no) -> do{add 2; item=1}while(1<=1?yes,stop) = 4 items
    assertEquals(4, result.size());
    assertEquals(16, result.get(0).intValue());
    assertEquals(8, result.get(1).intValue());
    assertEquals(4, result.get(2).intValue());
    assertEquals(2, result.get(3).intValue());
  }

  @Test
  void testUseExtendable()
  {
    Queryable<String> list = Queryable.as("One fish", "two fish", "red fish", "blue fish");
    Queryable<String> result = list.select(String::toUpperCase).use(CustomQuery.class).findFirstWordsOnly();
    assertEquals(4, result.size());
    assertEquals("ONE", result.get(0));
    assertEquals("TWO", result.get(1));
    assertEquals("RED", result.get(2));
    assertEquals("BLUE", result.get(3));
  }

  @Test
  void testMaxOnEmpty()
  {
    Queryable<Integer> q = new Queryable<>();
    Integer result = q.max(i -> i);
    assertNull(result);
  }

  @Test
  void testMinOnEmpty()
  {
    Queryable<Integer> q = new Queryable<>();
    Integer result = q.min(i -> i);
    assertNull(result);
  }

  @Test
  void testSumOnEmpty()
  {
    Queryable<Integer> q = new Queryable<>();
    assertEquals(0.0, q.sum(), 0.001);
  }

  @Test
  void testAverageOnEmpty()
  {
    Queryable<Integer> q = new Queryable<>();
    // average on empty divides by zero -> Infinity or NaN
    Double avg = q.average(i -> i);
    assertTrue(Double.isNaN(avg) || Double.isInfinite(avg));
  }

  @Test
  void testOrderByEmpty()
  {
    Queryable<Integer> q = new Queryable<>();
    Queryable<Integer> result = q.orderBy(i -> i);
    assertEquals(0, result.size());
  }

  @Test
  void testWhereOnEmpty()
  {
    Queryable<Integer> q = new Queryable<>();
    Queryable<Integer> result = q.where(i -> true);
    assertEquals(0, result.size());
  }

  @Test
  void testSelectOnEmpty()
  {
    Queryable<Integer> q = new Queryable<>();
    Queryable<String> result = q.select(i -> "x");
    assertEquals(0, result.size());
  }

  @Test
  void testEmptyListWithType()
  {
    List<String> strings = new ArrayList<>();
    Queryable<String> as = Queryable.as(strings, String.class);
    as.add("Lars");
    String[] asArray = as.asArray();
    assertArrayEquals(new String[]{"Lars"}, asArray);
  }

  @Test
  void testEmptyArray()
  {
    String[] strings = new String[0];
    Queryable<String> as = Queryable.as(strings);
    as.add("Lars");
    String[] asArray = as.asArray();
    assertArrayEquals(new String[]{"Lars"}, asArray);
  }

  @Test
  void testMaxSingleElement()
  {
    Queryable<String> q = Queryable.as("hello");
    String result = q.max(s -> s.length());
    assertEquals("hello", result);
  }

  @Test
  void testMinSingleElement()
  {
    Queryable<String> q = Queryable.as("hello");
    String result = q.min(s -> s.length());
    assertEquals("hello", result);
  }

  @Test
  void testAllSingleElementTrue()
  {
    Queryable<Integer> q = Queryable.as(2);
    assertTrue(q.all(i -> i % 2 == 0));
  }

  @Test
  void testAllSingleElementFalse()
  {
    Queryable<Integer> q = Queryable.as(3);
    assertFalse(q.all(i -> i % 2 == 0));
  }

  @Test
  void testAnySingleElementTrue()
  {
    Queryable<Integer> q = Queryable.as(2);
    assertTrue(q.any(i -> i % 2 == 0));
  }

  @Test
  void testAnySingleElementFalse()
  {
    Queryable<Integer> q = Queryable.as(3);
    assertFalse(q.any(i -> i % 2 == 0));
  }

  @Test
  void testSelectWithNullElements()
  {
    Queryable<String> q = Queryable.as("a", null, "b");
    Queryable<Integer> result = q.select(s -> s == null ? 0 : s.length());
    assertEquals(1, result.get(0).intValue());
    assertEquals(0, result.get(1).intValue());
    assertEquals(1, result.get(2).intValue());
  }

  @Test
  void testWhereWithNullElements()
  {
    Queryable<String> q = Queryable.as("a", null, "b");
    Queryable<String> result = q.where(s -> s != null);
    assertEquals(2, result.size());
  }

  @Test
  void testMaxWithTies()
  {
    Queryable<String> q = Queryable.as("hi", "ok", "no");
    String result = q.max(s -> s.length());
    assertEquals("hi", result); // first one wins
  }

  @Test
  void testMinWithTies()
  {
    Queryable<String> q = Queryable.as("hi", "ok", "no");
    String result = q.min(s -> s.length());
    assertEquals("hi", result); // first one wins
  }

  private void assertSameOrEqual(Object a, Object b)
  {
    assertTrue(a == b || a.equals(b));
  }
}
