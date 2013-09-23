package com.spun.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * A static class of convence functions for database access
 **/
public class DateUtils
{
  private static int TIME_SCALE[] = {Calendar.YEAR,
      Calendar.MONTH,
      Calendar.DATE,
      Calendar.HOUR_OF_DAY,
      Calendar.HOUR,
      Calendar.MINUTE,
      Calendar.SECOND,
      Calendar.MILLISECOND        };
  /************************************************************************/
  /**
   *
   **/
  public static boolean isSame(Date firstDate, Date secondDate, int smallestUnits)
  {
    if ((firstDate == null) || (secondDate == null)) { return (firstDate == secondDate); }
    return isSame(firstDate.getTime(), secondDate.getTime(), smallestUnits);
  }
  /************************************************************************/
  /**
   *
   **/
  public static boolean isSame(Calendar firstDate, Calendar secondDate, int smallestUnits)
  {
    if ((firstDate == null) || (secondDate == null)) { return (firstDate == secondDate); }
    return isSame(firstDate.getTime().getTime(), secondDate.getTime().getTime(), smallestUnits);
  }
  /************************************************************************/
  /**
   *
   **/
  public static boolean isSame(Date firstDate, Calendar secondDate, int smallestUnits)
  {
    if ((firstDate == null) || (secondDate == null)) { return ((Object) firstDate == (Object) secondDate); }
    return isSame(firstDate.getTime(), secondDate.getTime().getTime(), smallestUnits);
  }
  /************************************************************************/
  /**
   *
   **/
  public static boolean isSame(Calendar firstDate, Date secondDate, int smallestUnits)
  {
    if ((firstDate == null) || (secondDate == null)) { return ((Object) firstDate == (Object) secondDate); }
    return isSame(firstDate.getTime().getTime(), secondDate.getTime(), smallestUnits);
  }
  /************************************************************************/
  /**
   *
   **/
  public static boolean isSame(long firstDate, long secondDate, int smallestUnits)
  {
    if (!ArrayUtils.contains(TIME_SCALE, smallestUnits)) { throw new Error("Invalid Timescale " + smallestUnits); }
    GregorianCalendar first = new GregorianCalendar();
    first.setTime(new Date(firstDate));
    setSignificantDigit(first, smallestUnits);
    GregorianCalendar second = new GregorianCalendar();
    second.setTime(new Date(secondDate));
    setSignificantDigit(second, smallestUnits);
    //     My_System.variable("Testing if " + first.getTime().getTime() + "==" + second.getTime().getTime());
    return (first.getTime().getTime() == second.getTime().getTime());
  }
  /************************************************************************/
  public static Timestamp getStartOfYear()
  {
    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    DateUtils.setSignificantDigit(gregorianCalendar, Calendar.YEAR);
    return new Timestamp(gregorianCalendar.getTime().getTime());
  }
  /************************************************************************/
  public static Timestamp getStartOfToday()
  {
    return getStartOfXDaysAgo(0);
  }
  /************************************************************************/
  public static Timestamp getStartOfXDaysAgo(int numberOfDays)
  {
    return getStartOfXDaysAgo(numberOfDays, new Date());
  }
  /************************************************************************/
  public static Timestamp getStartOfXDaysAgo(int numberOfDays, Date startingFrom)
  {
    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    gregorianCalendar.setTime(startingFrom);
    DateUtils.setSignificantDigit(gregorianCalendar, Calendar.DATE);
    gregorianCalendar.add(Calendar.DATE, -numberOfDays);
    return new Timestamp(gregorianCalendar.getTime().getTime());
  }
  /************************************************************************/
  public static Calendar getEndOfTodayAsCalendar()
  {
    return rollToEndOfDay(new Date());
  }
  /************************************************************************/
  public static Timestamp getEndOfToday()
  {
    return new Timestamp(getEndOfTodayAsCalendar().getTime().getTime());
  }
  /************************************************************************/
  public static Calendar setSignificantDigit(Date date, int smallestUnits)
  {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTimeInMillis(date.getTime());
    return setSignificantDigit(calendar, smallestUnits);
  }
  /************************************************************************/
  public static Calendar setSignificantDigit(Calendar calendar, int smallestUnits)
  {
    boolean removeOn = false;
    for (int i = 0; i < TIME_SCALE.length; i++)
    {
      if (removeOn)
      {
        if (TIME_SCALE[i] == Calendar.DATE)
        {
          // Work arround since this is broke
          calendar.set(TIME_SCALE[i], 1);
        }
        else if (TIME_SCALE[i] == Calendar.HOUR_OF_DAY)
        {
          // Work arround since this is broke
          calendar.set(TIME_SCALE[i], 0);
        }
        else
        {
          calendar.clear(TIME_SCALE[i]);
        }
      }
      if (TIME_SCALE[i] == smallestUnits)
      {
        removeOn = true;
      }
    }
    return calendar;
  }
  /************************************************************************/
  public static Timestamp getStartOf(int unit, Date forDate)
  {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(forDate);
    setSignificantDigit(calendar, unit);
    return new Timestamp(calendar.getTimeInMillis());
  }
  /************************************************************************/
  public static Timestamp getEndOf(int unit, Date forDate)
  {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(forDate);
    setSignificantDigit(calendar, unit);
    calendar.add(unit, 1);
    calendar.add(Calendar.MILLISECOND, -1);
    return new Timestamp(calendar.getTimeInMillis());
  }
  /************************************************************************/
  public static boolean areSame(Date date1, Date date2, long accuracy)
  {
    return (compareDates(date1, date2) < accuracy);
  }
  /************************************************************************/
  /**
   * 
   * @return 1 if date1 > date2, 0 if date1 = date2, -1 if date1 < date2
   **/
  public static int compareDates(Date date1, Date date2)
  {
    long l1 = ((date1 == null) ? 0 : date1.getTime());
    long l2 = ((date2 == null) ? 0 : date2.getTime());
    long diff = l1 - l2;
    return (diff == 0) ? 0 : (int) (diff / Math.abs(diff));
  }
  /************************************************************************/
  public static Date createTime(Date date)
  {
    Calendar time = new GregorianCalendar();
    time.setTime(date);
    time.set(1970, 0, 1);
    return time.getTime();
  }
  /************************************************************************/
  /**
   * Rolls back till that time on a 24 hour clock
   **/
  public static Date rollTillHour(int hour, Date date)
  {
    Calendar rolled = new GregorianCalendar();
    rolled.setTime(date);
    setSignificantDigit(rolled, Calendar.HOUR_OF_DAY);
    while (rolled.get(Calendar.HOUR_OF_DAY) != hour)
    {
      rolled.add(Calendar.HOUR_OF_DAY, -1);
    }
    return rolled.getTime();
  }
  /************************************************************************/
  public static void main(String args[])
  {
    MySystem.variable("Calendar.DATE = " + Calendar.DATE);
    MySystem.variable("Year", setSignificantDigit(new GregorianCalendar(), Calendar.YEAR).getTime());
    MySystem.variable("Month", setSignificantDigit(new GregorianCalendar(), Calendar.MONTH).getTime());
    MySystem.variable("Day", setSignificantDigit(new GregorianCalendar(), Calendar.DAY_OF_MONTH).getTime());
    MySystem.variable("Hour", setSignificantDigit(new GregorianCalendar(), Calendar.HOUR).getTime());
    MySystem.variable("Minute", setSignificantDigit(new GregorianCalendar(), Calendar.MINUTE).getTime());
    MySystem.variable("End Of Day", rollToEndOfDay(new Date()).getTime());
  }
  /************************************************************************/
  public static GregorianCalendar rollToEndOfDay(Date date)
  {
    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    gregorianCalendar.setTime(date);
    gregorianCalendar.set(Calendar.HOUR_OF_DAY, 23);
    gregorianCalendar.set(Calendar.MINUTE, 59);
    gregorianCalendar.set(Calendar.SECOND, 59);
    gregorianCalendar.set(Calendar.MILLISECOND, 999);
    return gregorianCalendar;
  }
  /***********************************************************************/
  public static Timestamp asTimestamp(Date date)
  {
    return new Timestamp(date.getTime());
  }
  /************************************************************************/
  public static boolean isToday(Date date)
  {
    return DateUtils.isSame(date, new Date(), Calendar.DATE);
  }
  /************************************************************************/
  public static Calendar asCalendar(Date date)
  {
    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    gregorianCalendar.setTime(date);
    return gregorianCalendar;
  }
  /************************************************************************/
  public static Timestamp getLastOrCurrent(int dayOfWeek)
  {
    return getDayOfWeek(dayOfWeek, false);
    }
  /************************************************************************/
  public static Timestamp getNextOrCurrent(int dayOfWeek)
  {
    return getDayOfWeek(dayOfWeek, true);
  }
  /************************************************************************/
  private static Timestamp getDayOfWeek(int dayOfWeek, boolean foward) throws Error
  {
    int multiplier = foward  ? -1 : 1 ;
    for (int i = 0; i < 7; i++)
    {
      Timestamp day = getStartOfXDaysAgo(i * multiplier);
      if (asCalendar(day).get(Calendar.DAY_OF_WEEK) == dayOfWeek) { return day; }
    }
    throw new Error(String.format("didn't find a %s in the %s 7 days", dayOfWeek, foward ? "next":"last"));
  }
  /************************************************************************/
  /**
   * @param date "yyyy/MM/dd"
   */
  public static Timestamp parse(String date)
  {
    SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy/MM/dd");
    try
    {
      return asTimestamp(format.parse(date));
    }
    catch (ParseException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  /************************************************************************/
  /************************************************************************/
}