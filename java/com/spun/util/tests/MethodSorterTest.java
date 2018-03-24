package com.spun.util.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.junit.Test;
import org.lambda.query.OrderBy;

public class MethodSorterTest
{
  private UseCase[] useCases = {new UseCase(new String[]{"Bob", "Alice", "Charlie"},
      OrderBy.ascending(a -> a.toString()), new int[]{1, 0, 2}),
                                new UseCase(new String[]{"Bob", "Alice", "Charlie"},
                                    OrderBy.descending((String a) -> a.length()), new int[]{2, 1, 0}),
                                new UseCase(new Record[]{new Record("a"), new Record(null)},
                                    OrderBy.ascending((Record a) -> a.getValue()), new int[]{1, 0}),
                                new UseCase(new Integer[]{5, 7, 2}, OrderBy.ascending((Integer a) -> a.intValue()),
                                    new int[]{2, 0, 1}),
                                new UseCase(new Integer[]{5, 7, 2},
                                    OrderBy.ascending((Integer a) -> a.doubleValue()), new int[]{2, 0, 1})};
  /***********************************************************************/
  @Test
  public void test()
  {
    for (int i = 0; i < useCases.length; i++)
    {
      assertUseCase(useCases[i]);
    }
  }
  /***********************************************************************/
  private void assertUseCase(UseCase useCase)
  {
    //    MethodSorter methodSorter = new MethodSorter(useCase.startingArray[0].getClass(), useCase.sortOn,
    //        useCase.ascending);
    Arrays.sort(useCase.startingArray, useCase.compare);
    for (int i = 0; i < useCase.startingArray.length; i++)
    {
      assertEquals("Returned[" + i + "]", useCase.sortedArray[i], useCase.startingArray[i]);
    }
  }
  public static class UseCase<T>
  {
    public T[]            startingArray;
    public T[]            sortedArray;
    private Comparator<T> compare;
    public UseCase(T[] startingArray, java.util.Comparator<T> compare, int[] sortedOrder)
    {
      this.startingArray = startingArray;
      this.compare = compare;
      this.sortedArray = (T[]) makeSortedArray(startingArray, sortedOrder);
    }
    private Object[] makeSortedArray(T[] startingArray, int[] sortedOrder)
    {
      if (startingArray.length != sortedOrder.length) { throw new Error(
          "lengths not equal " + startingArray.length + " and " + sortedOrder.length); }
      ArrayList<T> sorted = new ArrayList<>();
      for (int i = 0; i < startingArray.length; i++)
      {
        sorted.add(startingArray[sortedOrder[i]]);
      }
      return sorted.toArray();
    }
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