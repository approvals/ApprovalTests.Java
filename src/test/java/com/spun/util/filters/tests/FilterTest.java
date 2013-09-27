package com.spun.util.filters.tests;

import java.util.Arrays;
import com.spun.util.ObjectUtils;
import com.spun.util.filters.Filter;
import com.spun.util.filters.FilterUtils;
import junit.framework.TestCase;

public class FilterTest extends TestCase
{
  public void testAll() throws Exception
  {
    Integer[] array = {1,2,3,4,5};
    FilterOdd filter = new FilterOdd();
    assertEquals("null, purified", 0, FilterUtils.retainPurified(array, null).size());
    assertEquals("null, extracted", 5, FilterUtils.retainExtracted(array, null).size());
    assertEquals("odd",3, FilterUtils.retainExtracted(array, filter).size());
    assertEquals("even", 2, FilterUtils.retainPurified(array, filter).size());
    assertEquals("odd",3, FilterUtils.retainExtracted(Arrays.asList(array), filter).size());
    assertEquals("even", 2, FilterUtils.retainPurified(Arrays.asList(array), filter).size());
  }
  
  
  private static class FilterOdd implements Filter {

    public boolean isExtracted(Object o) throws IllegalArgumentException
    {
      ObjectUtils.assertInstance(Integer.class, o);
      return ((Integer)o).intValue() % 2 == 1;
    }
    
  }
}
