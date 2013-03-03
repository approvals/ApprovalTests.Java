package com.spun.util.date;

import java.sql.Timestamp;
import java.util.Date;

import com.spun.util.DateUtils;

public class DateRangeUtils
{
  public static DateRange getDay(Timestamp date)
  {
    Date start = DateUtils.getStartOfXDaysAgo(0, date);
    Date end = DateUtils.getStartOfXDaysAgo(-1, date);
    DateRange range = new DateRange(start, end);
    return range;
  }
}
