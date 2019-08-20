package com.spun.util.tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.approvaltests.Approvals;
import org.junit.Test;

import com.spun.util.ArrayUtils;

public class ArrayUtilsTest
{
  @Test
  public void testAddManyToArray() throws Exception
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
  public void testAddToArray() throws Exception
  {
    Integer[] i = {5, 6, 7};
    Approvals.verifyAll("numbers", ArrayUtils.addToArray(i, 1));
  }
  @Test
  public void testCombine() throws Exception
  {
    List<Integer> list1 = Arrays.asList(1, 2, 3);
    List<Integer> list2 = Arrays.asList(5, 6, 7);
    assertEquals("[1, 2, 3, 5, 6, 7]", ArrayUtils.combine(list1, list2).toString());
  }
}
