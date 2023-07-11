package com.spun.util;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayUtilsTest
{
  @Test
  public void testAddManyToArray()
  {
    // begin-snippet: add_to_array
    Integer[] numbers = {1, 2, 3};
    numbers = ArrayUtils.addToArray(numbers, 4, 5, 6);
    // end-snippet
    // begin-snippet: add_to_array_result
    Integer[] resulting = {1, 2, 3, 4, 5, 6};
    // end-snippet
    assertArrayEquals(resulting, numbers);
  }
  @Test
  public void testAddToArray()
  {
    Integer[] i = {5, 6, 7};
    Approvals.verifyAll("numbers", ArrayUtils.addToArray(i, 1));
  }
  @Test
  public void testCombine()
  {
    List<Integer> list1 = Arrays.asList(1, 2, 3);
    List<Integer> list2 = Arrays.asList(5, 6, 7);
    assertEquals("[1, 2, 3, 5, 6, 7]", ArrayUtils.combine(list1, list2).toString());
  }
  @Test
  public void testCombineArrays()
  {
    Integer[] ints = new Integer[]{5, 6, 7};
    assertEquals("[1, 5, 6, 7]", Arrays.toString(ArrayUtils.combine(1, ints)));
    assertEquals("[1]", Arrays.toString(ArrayUtils.combine(1)));
    assertEquals("[null, a, b, c]", Arrays.toString(ArrayUtils.combine(null, "a", "b", "c")));
    String empty = null;
    assertEquals("[null]", Arrays.toString(ArrayUtils.combine(empty)));
  }
  public interface I
  {
  }
  public static class BrotherA implements I
  {
    @Override
    public String toString()
    {
      return "BrotherA";
    }
  }
  public static class BrotherB implements I
  {
    @Override
    public String toString()
    {
      return "BrotherB";
    }
  }
  public static class ChildOfA extends BrotherA
  {
    @Override
    public String toString()
    {
      return "ChildOfA";
    }
  }
  @Test
  void testCoAndContraVariance()
  {
    Integer nullObject = null;
    Object[][] result = new Object[][]{ArrayUtils.combine(new BrotherA(), new ChildOfA()),
                                       ArrayUtils.combine(new BrotherA(), new BrotherB()),
                                       ArrayUtils.combine(new BrotherB(), new BrotherA()),
                                       ArrayUtils.combine(new BrotherB(), new BrotherB()),
                                       ArrayUtils.combine(new ChildOfA(), new BrotherA()),
                                       ArrayUtils.combine(null, new BrotherA(), new ChildOfA()),
                                       ArrayUtils.combine(null, new BrotherA(), new BrotherA()),
                                       ArrayUtils.combine(null, new BrotherA(), new BrotherA(), new BrotherA()),
                                       ArrayUtils.combine(new BrotherA(), null, null),
                                       ArrayUtils.combine(new BrotherA(), new BrotherA[]{null}),
                                       ArrayUtils.combine(new BrotherA()),
                                       ArrayUtils.combine(null),
                                       ArrayUtils.combine(new BrotherA(), null),
                                       ArrayUtils.combine(nullObject, null),};
    Approvals.verifyAll("", result,
        a -> String.format("%s = %s", a.getClass().getSimpleName(), Arrays.toString(a)));
  }
  @Test
  void testToArray()
  {
    // begin-snippet: toArray
    List<Comparable> comparables = new ArrayList<>();
    comparables.add(null);
    comparables.add(1);
    comparables.add(3.1415);
    comparables.add("Lars");
    Comparable[] comparableArray = ArrayUtils.toArray(comparables);
    // end-snippet
    // begin-snippet: toArrayWithClass
    Comparable[] array = ArrayUtils.toArray(comparables, Comparable.class);
    // end-snippet
    Approvals.verifyAll("", comparableArray);
  }
}
