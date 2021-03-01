package org.lambda.query;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;
import org.lambda.Extendable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class QueryableTest
{
  // begin-snippet: implementing-extendable
  public static class CustomQuery implements Extendable<List<String>>
  {
    private List<String> caller;

    @Override public void setCaller(List<String> caller) {
      this.caller = caller;
    }
    // end-snippet

    // begin-snippet: extendable-query
    public Queryable<String> findFirstWordsOnly() {
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
  void example() {
    // begin-snippet: custom-query-example
    Queryable<String> list = Queryable.as("One fish", "two fish", "red fish", "blue fish");
    Queryable<String> firstWordsOnlyWithExtension = list.select(String::toUpperCase).use(CustomQuery.class).findFirstWordsOnly();
    // end-snippet
    // begin-snippet: custom-query-example-static
    Queryable<String> firstWordsOnlyStatic = CustomQuery.findFirstWordsOnly(Query.select(list, String::toUpperCase));
    // end-snippet
    assertArrayEquals(firstWordsOnlyStatic.toArray(), firstWordsOnlyWithExtension.toArray());
    Approvals.verifyAll("firstWordsOnly", firstWordsOnlyWithExtension);
  }

  @Test
  void testDistinct()
  {
    Queryable<Integer> distinct = Queryable.as(3, 2,1, 1, 3).distinct();
    Approvals.verifyAll("",distinct);
  }

  @Test
  void testToArray() {
    Character[] letters = Queryable.as('L', 'a','r', 's').asArray();
    Approvals.verifyAll("", letters);
  }
}
