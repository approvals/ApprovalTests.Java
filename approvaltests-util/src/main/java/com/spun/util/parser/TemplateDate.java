package com.spun.util.parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.spun.util.DateDifference;
import com.spun.util.DateUtils;
import com.spun.util.StringUtils;

public class TemplateDate
{
  public static final TemplateDate INSTANCE = new TemplateDate();
  public static final class FORMATS
  {
    public static final DateFormat DATE_SHORT_DAY  = new SimpleDateFormat("EEE MM/dd/yyyy");
    public static final DateFormat DATE_MONTH_YEAR = new SimpleDateFormat("MM/yy");
    public static final DateFormat DATE_MONTH_DAY  = new SimpleDateFormat("MM/dd");
    public static final DateFormat DATE_SHORT      = new SimpleDateFormat("MM/dd/yyyy");
    public static final DateFormat DATE_MEDIUM     = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
    public static final DateFormat DATE_LONG       = DateFormat.getDateInstance(DateFormat.LONG, Locale.US);
    public static final DateFormat DATE_FULL       = DateFormat.getDateInstance(DateFormat.FULL, Locale.US);
    public static final DateFormat TIME_SHORT      = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.US);
    public static final DateFormat TIME_FULL       = new SimpleDateFormat("H:mm");
    public static final DateFormat TIME_ZONE       = new SimpleDateFormat("H:mm z");
    public static final DateFormat TIME_MILLI      = new SimpleDateFormat("H:mm:ss:SSS");
  }
  //private static DateFormat dateTimeFormat = DateFormat.getDateTimeInstance(DateFormat.FULL ,DateFormat.SHORT);
  private Date           frozen     = null;
  private DateDifference difference = null;
  /************************************************************************/
  public TemplateDate()
  {
  }
  /************************************************************************/
  public TemplateDate(Date freezeTime)
  {
    this.frozen = freezeTime;
  }
  /************************************************************************/
  public void freezeTime()
  {
    frozen = new Date();
  }
  /***********************************************************************/
  public String getDate(String style)
  {
    return getDate(style, "");
  }
  /***********************************************************************/
  public String getDate(String style, String zone)
  {
    String value = null;
    DateFormat formatter = null;
    if (style.equalsIgnoreCase("FULL"))
    {
      formatter = FORMATS.DATE_FULL;
    }
    else if (style.equalsIgnoreCase("LONG"))
    {
      formatter = FORMATS.DATE_LONG;
    }
    else if (style.equalsIgnoreCase("MEDIUM"))
    {
      formatter = FORMATS.DATE_MEDIUM;
    }
    else if (style.equalsIgnoreCase("SHORTDAY"))
    {
      formatter = FORMATS.DATE_SHORT_DAY;
    }
    else if (style.equalsIgnoreCase("MonthYear"))
    {
      formatter = FORMATS.DATE_MONTH_YEAR;
    }
    else if (style.equalsIgnoreCase("MonthDay"))
    {
      formatter = FORMATS.DATE_MONTH_DAY;
    }
    else
    {
      formatter = FORMATS.DATE_SHORT;
    }
    formatter.setTimeZone((StringUtils.isNonZero(zone)) ? TimeZone.getTimeZone(zone) : TimeZone.getDefault());
    Date theDate = getDate(false);
    value = formatter.format(theDate);
    return value;
  }
  /***********************************************************************/
  public String getTime(String format)
  {
    return getTime(format, "");
  }
  /***********************************************************************/
  public String getTime(String format, String zone)
  {
    String value = null;
    DateFormat formatter = null;
    if (format.equalsIgnoreCase("AM/PM"))
    {
      formatter = FORMATS.TIME_SHORT;
    }
    else if (format.equalsIgnoreCase("Milli"))
    {
      formatter = FORMATS.TIME_MILLI;
    }
    else if (format.equalsIgnoreCase("Zone"))
    {
      formatter = FORMATS.TIME_ZONE;
    }
    else
    {
      formatter = FORMATS.TIME_FULL;
    }
    formatter.setTimeZone((StringUtils.isNonZero(zone)) ? TimeZone.getTimeZone(zone) : TimeZone.getDefault());
    value = formatter.format(getDate(false));
    return value;
  }
  /************************************************************************/
  public Date getDate(boolean forceCurrent)
  {
    if ((frozen != null) && (!forceCurrent))
    {
      return frozen;
    }
    else
    {
      return new Date();
    }
  }
  /************************************************************************/
  public Date getDate()
  {
    return getDate(false);
  }
  /***********************************************************************/
  public DateDifference getDifferenceFromToday()
  {
    if (difference == null)
    {
      difference = new DateDifference(getDate(false), new Date());
    }
    return difference;
  }
  /***********************************************************************/
  public String getDay()
  {
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(getDate(false));
    return StringUtils.padNumber(cal.get(Calendar.DAY_OF_MONTH), 2);
  }
  /***********************************************************************/
  public String getMonth(int offset)
  {
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(getDate(false));
    return StringUtils.padNumber(cal.get(Calendar.MONTH) + offset, 2);
  }
  /***********************************************************************/
  public String getMonthName()
  {
    return new SimpleDateFormat("MMMM").format(getDate(false));
  }
  /***********************************************************************/
  public String getYear()
  {
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(getDate(false));
    return StringUtils.padNumber(cal.get(Calendar.YEAR), 4);
  }
  /***********************************************************************/
  public boolean isToday()
  {
    return DateUtils.isToday(getDate(false));
  }
  /***********************************************************************/
  public String toString()
  {
    return this.getDate("", "");
  }
  /***********************************************************************/
  public String getDateAndTime(String dateFormat, String timeFormat)
  {
    return getDate(dateFormat) + " " + getTime(timeFormat);
  }
  /***********************************************************************/
  public String getDateAndTime()
  {
    return getDateAndTime("default", "default");
  }
  /************************************************************************/
  /************************************************************************/
}