package com.spun.util.filters;

import com.spun.util.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.lambda.query.Query;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilterTest
{
  @Test
  public void testAll()
  {
    Integer[] array = {1, 2, 3, 4, 5};
    FilterOdd filter = new FilterOdd();
    assertEquals(3, Query.where(array, i -> filter.isExtracted(i)).size(), "odd");
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
