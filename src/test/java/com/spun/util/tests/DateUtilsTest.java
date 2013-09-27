package com.spun.util.tests;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.TestCase;

import com.spun.util.DateUtils;

public class DateUtilsTest
  extends TestCase 
{
  private StartAndEndUseCases startAndEndUseCases[] = {new StartAndEndUseCases(Calendar.YEAR, "2003.05.03 15:20:20:123", "2003.01.01 00:00:00:000", "2003.12.31 23:59:59:999")};

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
      int day = (new GregorianCalendar().get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY) ? Calendar.THURSDAY : Calendar.TUESDAY;
      Timestamp next = DateUtils.getNextOrCurrent(day);
      Timestamp last = DateUtils.getLastOrCurrent(day);
      assertEquals("next thrusday",day, DateUtils.asCalendar(next).get(Calendar.DAY_OF_WEEK));
      assertEquals("last thrusday",day, DateUtils.asCalendar(last).get(Calendar.DAY_OF_WEEK));
      //MySystem.variable("next",next);
      //MySystem.variable("last",last);
      assertTrue("order for " + next + " after" + last, next.after(last));
    }
	/***********************************************************************/
  public static class StartAndEndUseCases
	{
		public int unit;
		public Date date;
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

