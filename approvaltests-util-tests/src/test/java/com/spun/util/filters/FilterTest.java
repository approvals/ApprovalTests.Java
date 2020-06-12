package com.spun.util.filters;

import org.lambda.query.Query;

import com.spun.util.ObjectUtils;
import com.spun.util.filters.Filter;

import junit.framework.TestCase;

public class FilterTest extends TestCase
{
  public void testAll() throws Exception
  {
    Integer[] array = {1, 2, 3, 4, 5};
    FilterOdd filter = new FilterOdd();
    assertEquals("odd", 3, Query.where(array, i -> filter.isExtracted(i)).size());
  }
  private static class FilterOdd implements Filter<Integer>
  {
    public boolean isExtracted(Integer o) throws IllegalArgumentException
    {
      ObjectUtils.assertInstance(Integer.class, o);
      return ((Integer) o).intValue() % 2 == 1;
    }
  }
}
