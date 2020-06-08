package com.spun.util.date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.lambda.functions.Function1;

import com.spun.util.DateUtils;
import com.spun.util.DeprecatedException;
import com.spun.util.ObjectUtils;
import com.spun.util.filters.Filter;
import com.spun.util.parser.TemplateDate;

public class DateRange
{
  public static final DateRange ALL = new DateRange(new Date(0), new Date(Long.MAX_VALUE));
  private final Date            end;
  private final Date            start;

  public DateRange(Date start, Date end)
  {
    this.start = start;
    this.end = end;
  }

  public Filter<?> getFilter(Class<?> clazz, String... methodCalls)
  {
    throw new DeprecatedException("getFilter(t -> t.%s())", methodCalls[0]);
  }

  public <T> Filter<T> getFilter(Function1<T, Date> converter)
  {
    return new DateRangeFilter<T>(this, converter);
  }

  public boolean contains(Date time)
  {
    time = (time == null) ? new Date() : time;
    return (start.getTime() <= time.getTime()) && (time.getTime() < end.getTime());
  }
  public static DateRange getRangeContaining(DateRange[] ranges, DateRange target)
  {
    for (DateRange dateRange : ranges)
    {
      if (dateRange.contains(target)) { return dateRange; }
    }
    return null;
  }
  private boolean contains(DateRange range)
  {
    return contains(range.end) || contains(range.start);
  }

  public Date getEnd()
  {
    return end;
  }

  public Date getStart()
  {
    return start;
  }

  @Override
  public int hashCode()
  {
    return ObjectUtils.generateHashCode(start.getTime(), end.getTime());
  }

  @Override
  public boolean equals(Object object)
  {
    if (this == object)
    {
      return true;
    }
    else if (object instanceof DateRange)
    {
      DateRange that = (DateRange) object;
      return this.start.getTime() == that.start.getTime() && this.end.getTime() == that.end.getTime();
    }
    else
    {
      return false;
    }
  }

  @Override
  public String toString()
  {
    return String.format("[%s - %s]", new TemplateDate(start).getDateAndTime(),
        new TemplateDate(end).getDateAndTime());
  }

  public DateRange[] getWeeks()
  {
    return getUnits(new WeekAware());
  }
  private DateRange[] getUnits(UnitAware unit)
  {
    ArrayList<DateRange> ranges = new ArrayList<DateRange>();
    Calendar start = DateUtils.asCalendar(getStart());
    Calendar end = (Calendar) start.clone();
    while (!(getEnd().getTime() <= end.getTime().getTime()))
    {
      end.add(Calendar.DAY_OF_YEAR, 1);
      if (unit.isStart(end) || getEnd().getTime() <= end.getTime().getTime())
      {
        ranges.add(new DateRange(start.getTime(), end.getTime()));
        start = (Calendar) end.clone();
      }
    }
    return ranges.toArray(new DateRange[ranges.size()]);
  }

  public DateRange[] getMonths()
  {
    return getUnits(new MonthAware());
  }
  public DateRange[] getQuarters()
  {
    return getUnits(new QuarterAware());
  }

  public boolean containsDayOfWeek(int day)
  {
    return getFirst(day) != null;
  }
  public Date getFirst(int day)
  {
    Calendar start = DateUtils.asCalendar(getStart());
    while (start.getTime().getTime() <= getEnd().getTime())
    {
      start.add(Calendar.DAY_OF_YEAR, 1);
      if (start.get(Calendar.DAY_OF_WEEK) == day) { return start.getTime(); }
    }
    return null;
  }

  /*                            INNER CLASSES                             */

  public static class DateRangeFilter<T> implements Filter<T>
  {
    private final Function1<T, Date> converter;
    private final DateRange          range;
    public DateRangeFilter(DateRange range, Function1<T, Date> converter)
    {
      this.range = range;
      this.converter = converter;
    }
    public boolean isExtracted(T t) throws IllegalArgumentException
    {
      return range.contains(converter.call(t));
    }
  }
  public static interface UnitAware
  {
    public boolean isStart(Calendar end);
  }
  public static class WeekAware implements UnitAware
  {
    public boolean isStart(Calendar end)
    {
      return end.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }
  }
  public static class MonthAware implements UnitAware
  {
    public boolean isStart(Calendar end)
    {
      return end.get(Calendar.DAY_OF_MONTH) == 1;
    }
  }
  public static class QuarterAware implements UnitAware
  {
    public boolean isStart(Calendar end)
    {
      return end.get(Calendar.DAY_OF_MONTH) == 1 && end.get(Calendar.MONTH) % 3 == 0;
    }
  }


}
