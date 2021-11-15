package com.spun.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.approvaltests.utils.WithTimeZone;
import org.junit.jupiter.api.Test;

public class DateUtilsTest
{
  private StartAndEndUseCases startAndEndUseCases[] = {new StartAndEndUseCases(Calendar.YEAR,
      "2003.05.03 15:20:20:123", "2003.01.01 00:00:00:000", "2003.12.31 23:59:59:999")};
  @Test
  public void testToDate()
  {
    try (WithTimeZone i = new WithTimeZone())
    {
      LocalDateTime time = LocalDateTime.of(2000, Month.JANUARY, 2, 3, 4, 5);
      assertEquals("2 Jan 2000 03:04:05 GMT", DateUtils.toDateInUTC(time).toGMTString());
      assertEquals("2 Jan 2000 02:04:05 GMT", DateUtils.toDate(time, ZoneOffset.ofHours(1)).toGMTString());
    }
  }
  @Test
  public void testStartAndEndUseCases()
  {
    for (int i = 0; i < startAndEndUseCases.length; i++)
    {
      assertStartAndEnd(startAndEndUseCases[i]);
    }
  }
  private void assertStartAndEnd(StartAndEndUseCases useCase)
  {
    assertEquals(useCase.start, DateUtils.getStartOf(useCase.unit, useCase.date), "Start date ");
    assertEquals(useCase.end, DateUtils.getEndOf(useCase.unit, useCase.date), "End date ");
  }
  @Test
  public void testNextAndLast()
  {
    int day = (new GregorianCalendar().get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY)
        ? Calendar.THURSDAY
        : Calendar.TUESDAY;
    Timestamp next = DateUtils.getNextOrCurrent(day);
    Timestamp last = DateUtils.getLastOrCurrent(day);
    assertEquals(day, DateUtils.asCalendar(next).get(Calendar.DAY_OF_WEEK), "next thrusday");
    assertEquals(day, DateUtils.asCalendar(last).get(Calendar.DAY_OF_WEEK), "last thrusday");
    //SimpleLogger.variable("next",next);
    //SimpleLogger.variable("last",last);
    assertTrue(next.after(last), "order for " + next + " after" + last);
  }
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
}
