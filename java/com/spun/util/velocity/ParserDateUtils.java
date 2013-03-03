package com.spun.util.velocity;

import java.util.Calendar;
import com.spun.util.DateUtils;
import com.spun.util.StringUtils;

/***********************************************************************/
public class ParserDateUtils
{
  public static final ParserDateUtils INSTANCE = new ParserDateUtils();
  public static Month[] getMonths()
  {
    return new Month[]{new Month("00", "------"), new Month("01", "Jan (01)"), new Month("02", "Feb (02)"), new Month("03", "Mar (03)"), new Month("04", "Apr (04)"), new Month("05", "May (05)"), new Month("06", "Jun (06)"), new Month("07", "Jul (07)"), new Month("08", "Aug (08)"),
        new Month("09", "Sep (09)"), new Month("10", "Oct (10)"), new Month("11", "Nov (11)"), new Month("12", "Dec (12)")};
  }
  /***********************************************************************/
  public static Day[] getDaysOfMonth()
  {
    Day[] days = new Day[32];
    days[0] = new Day("00", "--");
    for (int i = 1; i <= 31; i++)
    {
      days[i] = new Day(i);
    }
    return days;
  }
  /***********************************************************************/
  public static Year[] getNextXYears(int x)
  {
    return getNextXYears(x, 0);
  }
  /***********************************************************************/
  public static Year[] getNextXYears(int x, int backDateXDays)
  {
    Year[] years = new Year[x + 1];
    years[0] = new Year("0000", "----");
    int startingYear = DateUtils.asCalendar(DateUtils.getStartOfXDaysAgo(backDateXDays)).get(Calendar.YEAR);
    for (int i = 0; i < x; i++)
    {
      years[i + 1] = new Year(startingYear + i);
    }
    return years;
  }
  /***********************************************************************/
  /***********************************************************************/
  /***********************************************************************/
  public static class Year extends DateValue
  {    
    /***********************************************************************/
    public String getTwoDigitNumber()
    {
      return getNumber().substring(2);
    }
    public Year(String number, String displayText)
    {
      super(number, displayText);
    }
    /***********************************************************************/
    public Year(int i)
    {
      super("" + i, "" + i);
    }
   
  }
  /***********************************************************************/
  public static class Day extends DateValue
  {
    public Day(int i)
    {
      super(StringUtils.padNumber(i, 2), "" + i);
    }
    public Day(String number, String displayText)
    {
      super(number, displayText);
    }  
  }
  /***********************************************************************/
  public static class Month extends DateValue
  { 
    public Month(String number, String displayText)
    {
      super(number, displayText);
    }
  }
   /***********************************************************************/
  public static class DateValue
  {
    private String number;
    private String displayText;
    /***********************************************************************/
    public boolean isDefault()
    {
      return displayText.startsWith("--");
    }
     /***********************************************************************/
    public DateValue(String number, String displayText)
    {
      this.number= number;
      this.displayText = displayText;
    }
    /***********************************************************************/
    public String getNumber()
    {
      return number;
    }
    /***********************************************************************/
    public String getDisplayText()
    {
      return displayText;
    }
    /***********************************************************************/
  }
}
