package com.spun.util.filters.tests;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.spun.util.filters.FilterUtils;
import com.spun.util.filters.MethodFilter;
import junit.framework.TestCase;

public class MethodFilterTest extends TestCase
{
  public void testDate() throws Exception
  {
    SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
    Date dates[] = {dateParser.parse("2006-01-01"), dateParser.parse("2006-12-01")};
    Date date = dateParser.parse("2006-06-01");
    MethodFilter filter = new MethodFilter(Date.class,date,MethodFilter.CompareBy.GREATER_THAN_OR_EQUAL,"clone");
    ArrayList<Date> results = FilterUtils.retainExtracted(dates, filter);
    assertEquals(dates[0],results.get(0));
    assertEquals(1,results.size());
    
  }
}
