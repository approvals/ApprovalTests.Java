package com.spun.util.date.test;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import org.approvaltests.Approvals;

import com.spun.util.DateUtils;
import com.spun.util.date.DateRange;
import com.spun.util.filters.FilterUtils;
import com.spun.util.tests.TestUtils;

public class DateRangeTest extends TestCase
{
  /************************************************************************/
  public void testIsIn() throws Exception
  {
    DateRange range = new DateRange(date(40), date(20));
    assertTrue(range.contains(date(40)));
    assertTrue(range.contains(date(30)));
    assertFalse(range.contains(date(20)));
    assertFalse(range.contains(date(10)));
  }
  /************************************************************************/
  public void testFilter() throws Exception
  {
    DateRange range = new DateRange(date(40), date(20));
    Timestamp dates[] = {date(50), date(40), date(30), date(20), date(10)};
    TestUtils.assertLength(2, FilterUtils.retainExtracted(dates, range.getFilter(Timestamp.class, "clone")));
  }
  /************************************************************************/
  public static Timestamp date(int daysAgo)
  {
    return DateUtils.getStartOfXDaysAgo(daysAgo);
  }
  /************************************************************************/
  public void testGetWeeks() throws Exception
  {
    DateRange d = new DateRange(DateUtils.parse("2008/10/01"), DateUtils.parse("2008/11/01"));
    Approvals.verifyAll("week", d.getWeeks());
  }
  /************************************************************************/
  public void testContainsDayOfWeek() throws Exception
  {
    DateRange d = new DateRange(DateUtils.parse("2008/11/11"), DateUtils.parse("2008/11/15"));
    assertTrue(d.containsDayOfWeek(Calendar.THURSDAY));
    assertFalse(d.containsDayOfWeek(Calendar.MONDAY));
  }
  /************************************************************************/
  public void testGetFirstDayOfWeek() throws Exception
  {
    DateRange d = new DateRange(DateUtils.parse("2008/11/11"), DateUtils.parse("2008/11/15"));
    Date expected = new Date(DateUtils.parse("2008/11/13").getTime());
    assertEquals(expected, d.getFirst(Calendar.THURSDAY));
  }
  /************************************************************************/
  public void testGetMonths() throws Exception
  {
    DateRange d = new DateRange(DateUtils.parse("2008/01/01"), DateUtils.parse("2009/01/01"));
    Approvals.verifyAll("months", d.getMonths());
  }
  /************************************************************************/
  public void testGetQuarters() throws Exception
  {
    DateRange d = new DateRange(DateUtils.parse("2008/01/01"), DateUtils.parse("2009/01/01"));
    Approvals.verifyAll("months", d.getQuarters());
  }
  /************************************************************************/
  public void testGetRangeContaining() throws Exception
  {
    DateRange d = new DateRange(DateUtils.parse("2008/01/01"), DateUtils.parse("2009/01/01"));
    DateRange containing = DateRange.getRangeContaining(d.getQuarters(), d.getMonths()[0]);
    Approvals.verify(containing.toString());
  }
  
  /************************************************************************/
  /************************************************************************/
}
