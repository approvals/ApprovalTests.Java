package com.spun.util;



import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.lambda.query.OrderBy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderByTest
{
  @Test
  public void test()
  {
    assertUseCase(new String[]{"Bob", "Alice", "Charlie"}, OrderBy.ascending(a -> a.toString()), 1, 0, 2);
    assertUseCase(new String[]{"Bob", "Alice", "Charlie"}, OrderBy.descending((String a) -> a.length()), 2, 1, 0);
    assertUseCase(new Record[]{new Record("a"), new Record(null)}, OrderBy.ascending((Record a) -> a.getValue()),
        1, 0);
    assertUseCase(new Integer[]{5, 7, 2}, OrderBy.ascending((Integer a) -> a.intValue()), 2, 0, 1);
    assertUseCase(new Integer[]{5, 7, 2}, OrderBy.ascending((Integer a) -> a.doubleValue()), 2, 0, 1);
  }
  private <T> void assertUseCase(T[] startingArray, java.util.Comparator<T> compare, int... sortedOrder)
  {
    Object[] sortedArray = makeSortedArray(startingArray, sortedOrder);
    Arrays.sort(startingArray, compare);
    for (int i = 0; i < startingArray.length; i++)
    {
      assertEquals(sortedArray[i], startingArray[i], "Returned[" + i + "]");
    }
  }
  private <T> Object[] makeSortedArray(T[] startingArray, int[] sortedOrder)
  {
    if (startingArray.length != sortedOrder.length)
    { throw new Error("lengths not equal " + startingArray.length + " and " + sortedOrder.length); }
    ArrayList<T> sorted = new ArrayList<>();
    for (int i = 0; i < startingArray.length; i++)
    {
      sorted.add(startingArray[sortedOrder[i]]);
    }
    return sorted.toArray();
  }
  public static class Record
  {
    public String value;
    public Record(String value)
    {
      this.value = value;
    }
    public String getValue()
    {
      return value;
    }
  }
}