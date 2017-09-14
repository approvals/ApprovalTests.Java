package com.spun.util.tests;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.spun.util.DateUtils;

import junit.framework.TestCase;

public class DateUtilsTest extends TestCase
{
  private StartAndEndUseCases startAndEndUseCases[] = {new StartAndEndUseCases(Calendar.YEAR,
      "2003.05.03 15:20:20:123", "2003.01.01 00:00:00:000", "2003.12.31 23:59:59:999")};
  public void testToDate() throws Exception
  {
    LocalDateTime time = LocalDateTime.of(2000, Month.JANUARY, 2, 3, 4, 5);
    assertEquals("2 Jan 2000 03:04:05 GMT", DateUtils.toDateInUTC(time).toGMTString());
    assertEquals("2 Jan 2000 02:04:05 GMT", DateUtils.toDate(time, ZoneOffset.ofHours(1)).toGMTString());
  }
  /***********************************************************************/
  public void testStartAndEndUseCases()
  {
    for (int i = 0; i < startAndEndUseCases.length; i++)
    {
      assertStartAndEnd(startAndEndUseCases[i]);
    }
  }
  /***********************************************************************/
  private void assertStartAndEnd(StartAndEndUseCases useCase)
  {
    assertEquals("Start date ", useCase.start, DateUtils.getStartOf(useCase.unit, useCase.date));
    assertEquals("End date ", useCase.end, DateUtils.getEndOf(useCase.unit, useCase.date));
  }
  public void testNextAndLast() throws Exception
  {
    int day = (new GregorianCalendar().get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY)
        ? Calendar.THURSDAY
        : Calendar.TUESDAY;
    Timestamp next = DateUtils.getNextOrCurrent(day);
    Timestamp last = DateUtils.getLastOrCurrent(day);
    assertEquals("next thrusday", day, DateUtils.asCalendar(next).get(Calendar.DAY_OF_WEEK));
    assertEquals("last thrusday", day, DateUtils.asCalendar(last).get(Calendar.DAY_OF_WEEK));
    //SimpleLogger.variable("next",next);
    //SimpleLogger.variable("last",last);
    assertTrue("order for " + next + " after" + last, next.after(last));
  }
  /***********************************************************************/
  public static class StartAndEndUseCases
  {
    public int       unit;
    public Date      date;
    public Timestamp start;
    public Timestamp end;
    public StartAndEndUseCases(int unit, String date, String expectedStartDate, String expectedEndDate)
    {
      try
      {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss:SSS");
        this.unit = unit;
        this.date = formatter.parse(date);
        this.start = new Timestamp(formatter.parse(expectedStartDate).getTime());
        this.end = new Timestamp(formatter.parse(expectedEndDate).getTime());
      }
      catch (ParseException exception)
      {
        throw new Error(exception);
      }
    }
  }
  /***********************************************************************/
  /***********************************************************************/
}
