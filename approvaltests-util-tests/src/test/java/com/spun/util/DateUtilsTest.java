package com.spun.util;

import org.approvaltests.utils.WithTimeZone;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateUtilsTest
{
  private StartAndEndUseCases[] getStartAndEndUseCases()
  {
    return new StartAndEndUseCases[]{new StartAndEndUseCases(Calendar.YEAR, "2003.05.03 15:20:20:123",
        "2003.01.01 00:00:00:000", "2003.12.31 23:59:59:999")};
  }

  @Test
  public void testToDate()
  {
    try (WithTimeZone i = new WithTimeZone())
    {
      LocalDateTime time = LocalDateTime.of(2000, Month.JANUARY, 2, 3, 4, 5);
      assertEquals("02 Jan 2000 03:04:05 UTC", toUtc(DateUtils.toDateInUTC(time)));
      assertEquals("02 Jan 2000 02:04:05 UTC", toUtc(DateUtils.toDate(time, ZoneOffset.ofHours(1))));
    }
  }

  private String toUtc(Date toDate)
  {
    SimpleDateFormat sdf = new SimpleDateFormat();
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    sdf.applyPattern("dd MMM yyyy HH:mm:ss z");
    return sdf.format(toDate);
  }

  @Test
  public void testStartAndEndUseCases()
  {
    try (WithTimeZone t = new WithTimeZone())
    {
      StartAndEndUseCases[] startAndEndUseCases = getStartAndEndUseCases();
      for (int i = 0; i < startAndEndUseCases.length; i++)
      {
        assertStartAndEnd(startAndEndUseCases[i]);
      }
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
    try (WithTimeZone tz = new WithTimeZone())
    {
      int day = (new GregorianCalendar().get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY)
          ? Calendar.THURSDAY
          : Calendar.TUESDAY;
      Timestamp next = DateUtils.getNextOrCurrent(day);
      Timestamp last = DateUtils.getLastOrCurrent(day);
      assertEquals(day, DateUtils.asCalendar(next).get(Calendar.DAY_OF_WEEK), "next thursday");
      assertEquals(day, DateUtils.asCalendar(last).get(Calendar.DAY_OF_WEEK), "last thursday");
      assertTrue(next.after(last), "order for " + next + " after" + last);
    }
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
