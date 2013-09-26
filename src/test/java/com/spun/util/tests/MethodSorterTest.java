package com.spun.util.tests;

import java.util.Arrays;
import junit.framework.TestCase;
import com.spun.util.MethodSorter;

public class MethodSorterTest extends TestCase
{
  private UseCase[] useCases= {new UseCase(new String[]{"Bob","Alice", "Charlie"}, "toString", true, new int[]{1, 0, 2}), 
  		                         new UseCase(new String[]{"Bob","Alice", "Charlie"}, "length", false, new int[]{2, 1, 0}),
                               new UseCase(new Record[]{new Record("a"),new Record(null)}, "getValue", true, new int[]{1, 0}),
                               new UseCase(new Integer[]{5,7,2}, "intValue", true, new int[]{2, 0, 1}),
                               new UseCase(new Integer[]{5,7,2}, "doubleValue", true, new int[]{2, 0, 1})};
  
  /***********************************************************************/
  public void test()
  {
    for (int i=0; i < useCases.length; i++)
    {
      assertUseCase(useCases[i]);
    }
  }
	/***********************************************************************/
	private void assertUseCase(UseCase useCase)
	{
    MethodSorter methodSorter = new MethodSorter(useCase.startingArray[0].getClass(), useCase.sortOn, useCase.ascending);
		Arrays.sort(useCase.startingArray, methodSorter);
    for (int i = 0; i < useCase.startingArray.length; i++)
    {
      assertEquals("Returned[" + i + "]", useCase.sortedArray[i], useCase.startingArray[i]);
    }
		
	}
	/************************************************************************/
	public static void main(String[] args)
	{
		junit.textui.TestRunner.run(MethodSorterTest.class);
	}
	/***********************************************************************/
  /***********************************************************************/
  public static class UseCase
  { 
    public Object[] startingArray;
    public String sortOn;
    public boolean ascending;
    public Object[] sortedArray;
    public UseCase(Object[] startingArray, String sortOn, boolean ascending, int[] sortedOrder)
    {
      this.startingArray = startingArray;
      this.sortOn = sortOn;
      this.ascending = ascending;
      this.sortedArray = makeSortedArray(startingArray, sortedOrder);
    }
    /***********************************************************************/
    private static Object[] makeSortedArray(Object[] startingArray, int[] sortedOrder)
    {
      if (startingArray.length != sortedOrder.length)
      {
        throw new Error("lengths not equal " + startingArray.length + " and " + sortedOrder.length);   
      }
      Object[] objects = new Object[startingArray.length];
      for (int i = 0; i < startingArray.length; i++)
      {
        objects[i] = startingArray[sortedOrder[i]]; 
      }
      return objects;
    }
  }
  /***********************************************************************/
  public static class Record
  { 
    public String value;
    public Record(String value)
    {
      this.value = value;
    }
    /***********************************************************************/
    public String getValue()
    {
      return value;
    }
  }
}