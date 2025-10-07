package com.spun.util.timers;

import com.spun.util.ArrayUtils;
import com.spun.util.DateDifference;

/**
 * A Utility for counting things.
 **/
public class Counter
{
  private int    count     = 0;
  private long   lastTime  = 0;
  private long   startTime = 0;
  private String label     = null;
  public Counter()
  {
    this(null);
  }

  public Counter(String label)
  {
    this.label = label;
    this.startTime = System.currentTimeMillis();
    this.lastTime = this.startTime;
  }

  public void inc()
  {
    count++;
    lastTime = System.currentTimeMillis();
  }

  public void reset()
  {
    count = 0;
    lastTime = System.currentTimeMillis();
  }

  public int getCount()
  {
    return count;
  }

  public long getLastTime()
  {
    return lastTime;
  }

  public int getAverageClicksPerTime(long timeInMilli)
  {
    return (int) (getCount() / ((double) getTimeSinceStart() / timeInMilli));
  }

  public long getTimeSinceLast()
  {
    return System.currentTimeMillis() - lastTime;
  }

  public long getTimeSinceStart()
  {
    return System.currentTimeMillis() - startTime;
  }

  public DateDifference getLastTimeDifference()
  {
    return new DateDifference(System.currentTimeMillis() - lastTime);
  }

  public String toString()
  {
    return "Counter [Count, Time] = [" + count + ", " + getLastTimeDifference().getStandardTimeText(2) + "]";
  }

  public static Counter[] toArray(java.util.List counters)
  {
    return ArrayUtils.toArray(counters, Counter.class);
  }

  public String getLabel()
  {
    return label;
  }
}