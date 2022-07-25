package com.spun.util;

import com.spun.util.parser.TemplateDate;
import com.spun.util.parser.TemplateStringUtils;
import org.approvaltests.Approvals;
import org.approvaltests.utils.WithTimeZone;
import org.junit.jupiter.api.Test;
import org.lambda.functions.Function1;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateDifferenceTest
{
  private static final long YEAR_MS                      = 1000 * 60 * 60 * 24 * 365L;
  private static final long MONTH_MS                     = 1000 * 60 * 60 * 24 * 30L;
  // private static final long WEEK_MS   = 1000 * 60 * 60 * 24 * 7L;
  private static final long DAY_MS                       = 1000 * 60 * 60 * 24L;
  private static final long HOUR_MS                      = 1000 * 60 * 60L;
  private static final long MINUTE_MS                    = 1000 * 60L;
  private static final long SECOND_MS                    = 1000L;
  private long[][]          getRoundedDifferenceUseCases = {
                                                            // wanted,                 round,            milli,                     expected
                                                            {Calendar.MILLISECOND,
                                                             Calendar.YEAR,
                                                             YEAR_MS + 354,
                                                             354L},
                                                            {Calendar.MILLISECOND,
                                                             Calendar.YEAR,
                                                             YEAR_MS - 354,
                                                             YEAR_MS - 354},
                                                            {Calendar.DATE,
                                                             Calendar.YEAR,
                                                             YEAR_MS + 3 * DAY_MS,
                                                             3L},
                                                            {Calendar.DATE,
                                                             Calendar.YEAR,
                                                             YEAR_MS - 3 * DAY_MS,
                                                             (YEAR_MS - 3 * DAY_MS) / DAY_MS},
                                                            {Calendar.SECOND,
                                                             Calendar.HOUR,
                                                             HOUR_MS + 3 * SECOND_MS,
                                                             3L},
                                                            {Calendar.SECOND,
                                                             Calendar.HOUR,
                                                             HOUR_MS - 3 * SECOND_MS,
                                                             (HOUR_MS - 3 * SECOND_MS) / SECOND_MS},
                                                            {Calendar.SECOND,
                                                             Calendar.HOUR,
                                                             HOUR_MS + 3 * MINUTE_MS,
                                                             3 * MINUTE_MS / SECOND_MS},
                                                            {Calendar.SECOND,
                                                             Calendar.HOUR,
                                                             HOUR_MS - 3 * MINUTE_MS,
                                                             (HOUR_MS - 3 * MINUTE_MS) / SECOND_MS},
                                                            {Calendar.SECOND,
                                                             Calendar.MINUTE,
                                                             MINUTE_MS + 3 * SECOND_MS,
                                                             3L},
                                                            {Calendar.SECOND,
                                                             Calendar.MINUTE,
                                                             MINUTE_MS - 3 * SECOND_MS,
                                                             (MINUTE_MS - 3 * SECOND_MS) / SECOND_MS},};
  String[]                  unitsArray                   = {"Year",
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
  private GetTimeTextUseCase[] getTimeTextUseCases()
  {
    return new GetTimeTextUseCase[]{new GetTimeTextUseCase(5, Calendar.YEAR, Calendar.MILLISECOND, "now", "",
        unitsArray, YEAR_MS, "1 Year, 0 Months, 0 Weeks, 0 Days, 0 Hours"),
                                    new GetTimeTextUseCase(5, Calendar.YEAR, Calendar.MILLISECOND, "now", "",
                                        unitsArray, YEAR_MS + (2 * MONTH_MS),
                                        "1 Year, 2 Months, 0 Weeks, 0 Days, 0 Hours"),
                                    new GetTimeTextUseCase(5, Calendar.YEAR, Calendar.MILLISECOND, "now", "",
                                        unitsArray, YEAR_MS - (2 * MONTH_MS),
                                        "10 Months, 0 Weeks, 5 Days, 0 Hours, 0 Mins"),
                                    new GetTimeTextUseCase(2, Calendar.YEAR, Calendar.MILLISECOND, "", "ago",
                                        unitsArray, YEAR_MS + (2 * MONTH_MS), "1 Year, 2 Months ago"),
                                    new GetTimeTextUseCase(5, Calendar.MILLISECOND, Calendar.MILLISECOND, "now",
                                        "", unitsArray, YEAR_MS + (2 * MONTH_MS), "36720000000 Millis"),
                                    new GetTimeTextUseCase(5, Calendar.YEAR, Calendar.DATE, "today", "ago",
                                        unitsArray, DAY_MS, "1 Day ago"),
                                    new GetTimeTextUseCase(5, Calendar.YEAR, Calendar.DATE, "today", "ago",
                                        unitsArray, DAY_MS - 1, "today"),
                                    new GetTimeTextUseCase(5, Calendar.YEAR, Calendar.MILLISECOND, "now", "",
                                        unitsArray, new GregorianCalendar(2003, 1, 28), /* 1 = Feb */
                                        new GregorianCalendar(2003, 2, 31),
                                        "1 Month, 0 Weeks, 1 Day, 0 Hours, 0 Mins"),
                                    new GetTimeTextUseCase(5, Calendar.YEAR, Calendar.MILLISECOND, "now", "",
                                        unitsArray, new GregorianCalendar(2003, 1, 1),
                                        new GregorianCalendar(2003, 2, 1),
                                        "4 Weeks, 0 Days, 0 Hours, 0 Mins, 0 Secs"),
                                    new GetTimeTextUseCase(5, Calendar.YEAR, Calendar.MILLISECOND, "now", "",
                                        unitsArray, new GregorianCalendar(2004, 1, 1),
                                        new GregorianCalendar(2004, 2, 1),
                                        "4 Weeks, 1 Day, 0 Hours, 0 Mins, 0 Secs"),
                                    new GetTimeTextUseCase(5, Calendar.MONTH, Calendar.MILLISECOND, "now", "",
                                        unitsArray, new GregorianCalendar(1990, 0, 1), /* 7 * 12 should be 84 */
                                        new GregorianCalendar(1997, 0, 1),
                                        "85 Months, 1 Week, 0 Days, 0 Hours, 0 Mins"),
                                    new GetTimeTextUseCase(5, Calendar.DATE, Calendar.MILLISECOND, "now", "",
                                        unitsArray,
                                        new GregorianCalendar(2004, 3, 3, 10,
                                            0), /* Daylight savings is in month '3' */
                                        new GregorianCalendar(2004, 3, 4, 11, 0),
                                        "1 Day, 0 Hours, 0 Mins, 0 Secs, 0 Millis")};
  }
  @Test
  public void testGetRoundedDifference()
  {
    for (int i = 0; i < getRoundedDifferenceUseCases.length; i++)
    {
      int wanted = (int) getRoundedDifferenceUseCases[i][0];
      int round = (int) getRoundedDifferenceUseCases[i][1];
      long milli = getRoundedDifferenceUseCases[i][2];
      long expected = getRoundedDifferenceUseCases[i][3];
      DateDifference d = new DateDifference(milli);
      assertEquals(expected, (Object) d.getRemainingDifference(wanted, round),
          "getRoundedDifference(" + wanted + ", " + round + ") on " + milli);
    }
  }
  @Test
  public void testFebruaryAndDaylightSavingsTime()
  {
    try (WithTimeZone tz = new WithTimeZone("PST"))
    {
      StringBuilder buffer = new StringBuilder();
      DateFormat f = TemplateDate.FORMATS.DATE_SHORT;
      for (int i = 1; i <= 28; i++)
      {
        Timestamp a = DateUtils.parse("2010/02/0" + i);
        Timestamp b = DateUtils.parse("2010/03/0" + i);
        DateDifference dif = new DateDifference(a, b);
        String standardTimeText = dif.getStandardTimeText(2, "days", "seconds", null, null);
        buffer.append(String.format("%s, %s => %s\n", f.format(a), f.format(b), standardTimeText));
      }
      Approvals.verify(buffer.toString());
    }
  }
  @Test
  public void testGetTimeText()
  {
    try (WithTimeZone tz = new WithTimeZone())
    {
      Approvals.verifyAll("getTimeText", getTimeTextUseCases(),
          useCase -> useCase + " -> " + new DateDifference(useCase.milli).getTimeText(useCase.amount,
              useCase.maxUnit, useCase.minUnit, useCase.nowText, useCase.agoText, useCase.units));
    }
  }
  private class GetTimeTextUseCase
  {
    int      amount;
    int      maxUnit;
    int      minUnit;
    String   nowText;
    String   agoText;
    String[] units;
    long     milli;
    String   expected;
    public GetTimeTextUseCase(int amount, int maxUnit, int minUnit, String nowText, String agoText, String[] units,
        long milli, String expected)
    {
      this.init(amount, maxUnit, minUnit, nowText, agoText, units, milli, expected);
    }
    public GetTimeTextUseCase(int amount, int maxUnit, int minUnit, String nowText, String agoText, String[] units,
        Calendar date1, Calendar date2, String expected)
    {
      long time = date1.getTimeInMillis() - date2.getTimeInMillis();
      if (time < 0)
      {
        time = -time;
      }
      this.init(amount, maxUnit, minUnit, nowText, agoText, units, time, expected);
    }
    private void init(int amount, int maxUnit, int minUnit, String nowText, String agoText, String[] units,
        long milli, String expected)
    {
      this.amount = amount;
      this.maxUnit = maxUnit;
      this.minUnit = minUnit;
      this.nowText = nowText;
      this.agoText = agoText;
      this.units = units;
      this.milli = milli;
      this.expected = expected;
    }
    @Override
    public String toString()
    {
      Function1<String, String> pad = s -> TemplateStringUtils.pad(s, 6, " ");
      Function1<String, String> printEmpty = s -> pad.call(s.isEmpty() ? "\"\"" : s);
      return MessageFormat.format("({0}, {1}, {2}, {3}, {4}) on {5}", amount, units[maxUnit], units[minUnit],
          printEmpty.call(nowText), printEmpty.call(agoText), StringUtils.padNumber(milli, 12));
    }
  }
}
