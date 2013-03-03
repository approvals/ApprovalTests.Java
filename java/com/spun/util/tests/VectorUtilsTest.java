package com.spun.util.tests;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.approvaltests.Approvals;

import com.spun.util.ArrayUtils;

public class VectorUtilsTest extends TestCase
{
  public void testAddToArray() throws Exception
  {
    Integer[] i = {5, 6, 7};
    Approvals.verifyAll("numbers", ArrayUtils.addToArray(i, 1));
  }
  public void testCombine() throws Exception
  {
    List<Integer> list1 = Arrays.asList(1, 2, 3);
    List<Integer> list2 = Arrays.asList(5, 6, 7);
    assertEquals("[1, 2, 3, 5, 6, 7]", ArrayUtils.combine(list1, list2).toString());
  }
}
