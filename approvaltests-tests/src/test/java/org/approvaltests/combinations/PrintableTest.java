package org.approvaltests.combinations;

import org.approvaltests.Approvals;
import org.approvaltests.strings.Printable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrintableTest
{
  @Test
  void testSingles()
  {
    // begin-snippet: printable_single_label
    Printable<Integer> one = new Printable(1, "one");
    assertEquals("one", one.toString());
    // end-snippet
    // begin-snippet: printable_access
    Integer value = one.get();
    assertEquals(1, value);
    // end-snippet
    // begin-snippet: printable_single_lambda
    Printable<Integer> two = new Printable(2, i -> "#" + i + ")");
    assertEquals("#2)", two.toString());
    // end-snippet
  }

  @Test
  void testOverridingToString()
  {
    Integer[] p1 = {1, 2, 3, 4, 5};
    CombinationApprovals.verifyAllCombinations(n -> n.get(), Printable.create(n -> "#" + n, p1));
  }

  @Test
  void testCreate()
  {
    // begin-snippet: printable_array_lambda
    Printable<Integer>[] numbers = Printable.create(n -> "#" + n, 1, 2, 3, 4, 5);
    Approvals.verifyAll("Custom toString method", numbers, p -> String.format("%s -> %s", p, p.get()));
    // end-snippet
  }

  @Test
  void testLabels()
  {
    // begin-snippet: printable_array_labels
    Printable<Integer>[] labeled = Printable.with().label(1, "first").label(2, "second").label(3, "third")
        .label(4, "forth").label(5, "fifth").toArray();
    Approvals.verifyAll("Labeled", labeled, p -> String.format("%s -> %s", p, p.get()));
    // end-snippet
  }
}
