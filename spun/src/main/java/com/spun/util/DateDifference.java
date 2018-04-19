package com.spun.util;

import java.util.Calendar;
import java.util.Date;

import com.spun.util.logger.SimpleLogger;

public class DateDifference
{
  public static final String STANDARD_TIME_TEXT[] = {"Year",
                                                     "Years",
                                                     "Month",
                                                     "Months",
                                                     "Week",
                                                     "Weeks",
                                                     "Day",
                                                     "Days",
                                                     "Hour",
                                                     "Hours",
                                                     "Min",
                                                     "Mins",
                                                     "Sec",
                                                     "Secs",
                                                     "Milli",
                                                     "Millis"};
  public static final String MILLISECONDS         = "milliseconds";
  public static final String SECONDS              = "seconds";
  public static final String MINUTES              = "minutes";
  public static final String HOURS                = "hours";
  public static final String DAYS                 = "days";
  public static final String WEEKS                = "weeks";
  public static final String MONTHS               = "months";
  public static final String YEARS                = "years";
  public static String       TIME_UNITS[]         = {YEARS,
                                                     MONTHS,
                                                     WEEKS,
                                                     DAYS,
                                                     HOURS,
                                                     MINUTES,
                                                     SECONDS,
                                                     MILLISECONDS};
  // assumes a 30 day month
  private static int         TIME_SCALE[]         = {Calendar.YEAR,
                                                     Calendar.MONTH,
                                                     Calendar.WEEK_OF_YEAR,
                                                     Calendar.DATE,
                                                     Calendar.HOUR,
                                                     Calendar.MINUTE,
                                                     Calendar.SECOND,
                                                     Calendar.MILLISECOND};
  private static long        DIVIDERS[]           = {1000 * 60 * 60 * 24 * 365L,
                                                     1000 * 60 * 60 * 24 * 30L,
                                                     1000 * 60 * 60 * 24 * 7L,
                                                     1000 * 60 * 60 * 24L,
                                                     1000 * 60 * 60L,
                                                     1000 * 60L,
                                                     1000L,
                                                     1L};
  /************************************************************************/
  private long               milli;
  /************************************************************************/
  public DateDifference(long timeDifference)
  {
    this.milli = timeDifference;
  }
  /************************************************************************/
  public DateDifference(Date date1, Date date2)
  {
    milli = date1.getTime() - date2.getTime();
    if (milli < 0)
    {
      milli = milli * -1;
    }
    //    My_System.variable("dateDifference = " + milli);
  }
  /************************************************************************/
  /**
   * Debugging tool.
   **/
  public static void printDividers()
  {
    SimpleLogger.variable("max long = " + Long.MAX_VALUE);
    for (int i = 0; i < DIVIDERS.length; i++)
    {
      SimpleLogger.variable("" + DIVIDERS[i]);
    }
  }
  /************************************************************************/
  /**
   * Gets the amount of [Units]. <BR>
   * i.e. <BR>
   * Given a  DateDifference of 2 Days 3 Hours 4 Minutes <BR>
   * getAbsoluteDifference(Calendar.DATE) = 2
   * getAbsoluteDifference(Calendar.HOUR) = 51
   **/
  public long getAbsoluteDifference(int unit)
  {
    return getAbsoluteDifference(unit, this.milli);
  }
  /************************************************************************/
  public long getAbsoluteDifference(String unit)
  {
    return getAbsoluteDifference(convertUnitString(unit));
  }
  /************************************************************************/
  public long getRoundedDifference(String unit)
  {
    return getRoundedDifference(convertUnitString(unit), this.milli);
  }
  /************************************************************************/
  public long getRoundedDifference(int unit)
  {
    return getRoundedDifference(unit, this.milli);
  }
  /************************************************************************/
  public static long getAbsoluteDifference(int unit, long time)
  {
    //    My_System.variable("divider = " + DIVIDERS[getIndex(unit)]);
    return time / DIVIDERS[getTimeScaleIndex(unit)];
  }
  /************************************************************************/
  public static long getRoundedDifference(int unit, long time)
  {
    return Math.round(((double) time) / DIVIDERS[getTimeScaleIndex(unit)]);
  }
  /************************************************************************/
  /**
   * Gets the remaining amount of [Units]. <BR>
   * i.e. <BR>
   * Given a  DateDifference of 1 Month 2 Weeks 3 Days, 
   * getRemainingDifference(days, months) = 17 days <BR>
   * getRemainingDifference(days, weeks) = 3 days <BR>
   **/
  public long getRemainingDifference(int wantedUnit, int roundTo)
  {
    return getRemainingDifference(wantedUnit, roundTo, this.milli);
  }
  /************************************************************************/
  /************************************************************************/
  public static long getRemainingDifference(int wantedUnit, int roundTo, long time)
  {
    int wantedIndex = getTimeScaleIndex(wantedUnit);
    int roundToIndex = getTimeScaleIndex(roundTo);
    if (wantedIndex < roundToIndex) { throw new Error(
        "Can't round with a smaller Unit.[" + TIME_UNITS[wantedIndex] + ", " + TIME_UNITS[roundToIndex] + "]"); }
    //roundedDifference = (millis % (roundTo in millis)) / (unit in millis)
    return (time % DIVIDERS[roundToIndex]) / DIVIDERS[wantedIndex];
  }
  /***********************************************************************/
  /**
   * @return the index in TIME_SCALE[] of the largest Unit to be > 0
   **/
  private int getMaximumTimeUnit()
  {
    int i = 0;
    while ((i < DIVIDERS.length) && (milli < DIVIDERS[i]))
    {
      i++;
    }
    return i;
  }
  /***********************************************************************/
  /**
   * Finds the index for a Calendar.DATE ect.
   * @return the index in TIME_SCALE[] 
   **/
  public static int getTimeScaleIndex(int calendarTime)
  {
    int i = 0;
    while (calendarTime != TIME_SCALE[i])
    {
      i++;
    }
    return i;
  }
  /***********************************************************************/
  public static int convertUnitString(String unit)
  {
    int result = 0;
    if (MILLISECONDS.equalsIgnoreCase(unit))
    {
      result = Calendar.MILLISECOND;
    }
    else if (SECONDS.equalsIgnoreCase(unit))
    {
      result = Calendar.SECOND;
    }
    else if (MINUTES.equalsIgnoreCase(unit))
    {
      result = Calendar.MINUTE;
    }
    else if (HOURS.equalsIgnoreCase(unit))
    {
      result = Calendar.HOUR;
    }
    else if (DAYS.equalsIgnoreCase(unit))
    {
      result = Calendar.DATE;
    }
    else if (WEEKS.equalsIgnoreCase(unit))
    {
      result = Calendar.WEEK_OF_YEAR;
    }
    else if (MONTHS.equalsIgnoreCase(unit))
    {
      result = Calendar.MONTH;
    }
    else if (YEARS.equalsIgnoreCase(unit))
    {
      result = Calendar.YEAR;
    }
    return result;
  }
  /***********************************************************************/
  public static long convertUnitStringToMilli(String unit)
  {
    int cal = convertUnitString(unit);
    return DIVIDERS[getTimeScaleIndex(cal)];
  }
  /***********************************************************************/
  public boolean isMoreThan(int amount, int unit)
  {
    return (getAbsoluteDifference(unit) >= amount);
  }
  /***********************************************************************/
  public boolean isMoreThan(int amount, String unitString)
  {
    return (getAbsoluteDifference(convertUnitString(unitString)) >= amount);
  }
  /***********************************************************************/
  public long getStandardRoundedTime(int unitIndex, boolean forceAbsolute)
  {
    return getStandardRoundedTime(unitIndex, forceAbsolute, this.milli);
  }
  /***********************************************************************/
  private static long getStandardRoundedTime(int unitIndex, boolean forceAbsolute, long time)
  {
    if (unitIndex == 0 || forceAbsolute)
    {
      return getAbsoluteDifference(TIME_SCALE[unitIndex], time);
    }
    else
    {
      return getRemainingDifference(TIME_SCALE[unitIndex], TIME_SCALE[unitIndex - 1], time);
    }
  }
  /***********************************************************************/
  public String getStandardTimeText(int amount, String maxUnit, String minUnit, String nowText, String agoText)
  {
    return getTimeText(amount, convertUnitString(maxUnit), convertUnitString(minUnit), nowText, agoText,
        STANDARD_TIME_TEXT);
  }
  /***********************************************************************/
  public String getTimeText(int amount, int maxUnit, int minUnit, String nowText, String agoText, String units[])
  {
    //		My_System.variable("amount = " + amount + ", maxUnit = " + maxUnit +  ", minUnit = " + minUnit + ", nowText = " + nowText);
    if (amount == 0) { throw new Error("getTimeText() requires amount > 0"); }
    maxUnit = getTimeScaleIndex(maxUnit);
    minUnit = getTimeScaleIndex(minUnit);
    int realMax = getMaximumTimeUnit();
    String timeText = nowText;
    if (realMax < maxUnit)
    {
      realMax = maxUnit;
    }
    if (realMax <= minUnit)
    {
      timeText = "";
      long remainingTime = this.milli;
      for (int i = realMax; i < (realMax + amount) && (i <= minUnit); i++)
      {
        long time = getStandardRoundedTime(i, i == realMax, remainingTime);
        remainingTime -= time * DIVIDERS[i];
        timeText += time + " " + units[(time == 1) ? i * 2 : i * 2 + 1] + ", ";
        //My_System.variable("timeText = " + timeText);
      }
      timeText = timeText.substring(0, timeText.length() - 2);
      if ((agoText != null) && (agoText.length() > 0))
      {
        timeText += " " + agoText;
      }
    }
    return timeText;
  }
  /***********************************************************************/
  public String getClockTimeText()
  {
    int maxUnit = getTimeScaleIndex(Calendar.HOUR);
    int minUnit = getTimeScaleIndex(Calendar.MILLISECOND);
    int realMax = getMaximumTimeUnit();
    realMax = (realMax < maxUnit) ? maxUnit : realMax;
    String timeText = "";
    if (realMax <= minUnit)
    {
      for (int i = realMax; i <= minUnit; i++)
      {
        int padding = (i == getTimeScaleIndex(Calendar.MILLISECOND)) ? 3 : 2;
        long time = getStandardRoundedTime(i, i == realMax);
        if (i == realMax)
        {
          timeText += time + ":";
        }
        else
        {
          timeText += StringUtils.padNumber(time, padding) + ":";
        }
      }
      timeText = timeText.substring(0, timeText.length() - 1);
    }
    return timeText;
  }
  /***********************************************************************/
  public String getStandardTimeText(int amountShown)
  {
    return getTimeText(amountShown, Calendar.YEAR, Calendar.MILLISECOND, "now", "", STANDARD_TIME_TEXT);
  }
  /************************************************************************/
  /************************************************************************/
}