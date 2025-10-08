package com.spun.util.timers;

import com.spun.util.DateDifference;

import java.io.Serializable;

/**
 * A Utility for timing an event multiple times.
 * Holds min, max, timeouts. etc...
 **/
public class EventTime
{
  public enum SortBy {
                      NAME, COUNT, TOTAL_TIME, AVERAGE_TIME, MIN_TIME, MAX_TIME, EXCEEDED_COUNT
  }
  private static final String[] enumeration       = {"unknown",
                                                     "name",
                                                     "count",
                                                     "total time",
                                                     "average time",
                                                     "min time",
                                                     "max time",
                                                     "excceded count"};
  private int                   count             = 0;
  private long                  totalTime         = 0;
  private String                label             = null;
  private long                  maxTime           = 0;
  private long                  minTime           = Long.MAX_VALUE;
  private long                  timeLimit         = 0;
  private Counter               timeLimitExceeded = null;
  public EventTime()
  {
  }

  public EventTime(String eventName, long timeLimit)
  {
    this.label = eventName;
    this.timeLimit = timeLimit;
    this.timeLimitExceeded = (timeLimit > 0) ? new Counter() : null;
  }

  public static int convertEnumerationString(String enumm)
  {
    int found = 0;
    for (int i = 0; i < enumeration.length; i++)
    {
      if (enumeration[i].equalsIgnoreCase(enumm))
      {
        found = i;
        break;
      }
    }
    return found;
  }

  public void add(long time)
  {
    totalTime += time;
    count++;
    maxTime = (time > maxTime) ? time : maxTime;
    minTime = (time < minTime) ? time : minTime;
    if ((timeLimitExceeded != null) && (time > timeLimit))
    {
      timeLimitExceeded.inc();
    }
  }

  public void reset()
  {
    maxTime = 0;
    minTime = Long.MAX_VALUE;
    count = 0;
    totalTime = 0;
  }

  public int getCount()
  {
    return count;
  }

  public String getLabel()
  {
    return label;
  }

  public long getTotalTime()
  {
    return totalTime;
  }

  public long getMaxTime()
  {
    return maxTime;
  }

  public DateDifference getMaxTimeAsDateDifference()
  {
    return new DateDifference(maxTime);
  }

  public long getTimeLimit()
  {
    return timeLimit;
  }

  public DateDifference getTimeLimitAsDateDifference()
  {
    return new DateDifference(timeLimit);
  }

  public Counter getTimeLimitExceededCounter()
  {
    return timeLimitExceeded;
  }

  public long getMinTime()
  {
    return minTime;
  }

  public DateDifference getMinTimeAsDateDifference()
  {
    return new DateDifference(minTime);
  }

  public long getAverageTime()
  {
    return (getCount() == 0) ? 0 : getTotalTime() / getCount();
  }

  public DateDifference getAverageTimeAsDateDifference()
  {
    return new DateDifference(getAverageTime());
  }

  public String toString()
  {
    String value = "com.spun.util.timers.EventTime[";
    value += " count = " + count + ",\n" + " label = '" + label + "'" + ",\n" + " maxTime = " + maxTime + ",\n"
        + " minTime = " + minTime + ",\n" + " timeLimit = " + timeLimit + ",\n" + " totalTime = " + totalTime
        + "]";
    return value;
  }
  /**                     Inner Classes                                  **/
  /**
   * For use with java.util.Arrays.sort(Trade[], Trade.SortAddDate).
   **/
  public static class SortEventTimers implements java.util.Comparator<EventTime>, Serializable
  {
    private static final long serialVersionUID = 1L;
    private SortBy            type             = null;
    private int               asc              = 0;
    public SortEventTimers(SortBy type, boolean asc)
    {
      this.type = type;
      this.asc = asc ? 1 : -1;
    }

    public int compare(EventTime o1, EventTime o2) throws java.lang.ClassCastException
    {
      if ((o1 instanceof EventTime) && (o2 instanceof EventTime))
      {
        EventTime et1 = (EventTime) o1;
        EventTime et2 = (EventTime) o2;
        switch (type)
        {
          case NAME :
            return asc * et1.getLabel().compareToIgnoreCase(et2.getLabel());
          case COUNT :
            return asc * (et1.getCount() - et2.getCount());
          case TOTAL_TIME :
            return asc * (int) (et1.getTotalTime() - et2.getTotalTime());
          case AVERAGE_TIME :
            return asc * (int) (et1.getAverageTime() - et2.getAverageTime());
          case MIN_TIME :
            return asc * (int) (et1.getMinTime() - et2.getMinTime());
          case MAX_TIME :
            return asc * (int) (et1.getMaxTime() - et2.getMaxTime());
          case EXCEEDED_COUNT :
            return asc
                * (et1.getTimeLimitExceededCounter().getCount() - et2.getTimeLimitExceededCounter().getCount());
          default :
            return 0;//this cannot be reached
        }
      }
      else
      {
        throw new java.lang.ClassCastException("Tried to compare apples and oranges in SortAddDate");
      }
    }
  }
}
