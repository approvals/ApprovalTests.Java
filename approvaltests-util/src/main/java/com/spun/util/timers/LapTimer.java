package com.spun.util.timers;

import com.spun.util.DateDifference;
import com.spun.util.logger.SimpleLogger;

import java.util.ArrayList;

/**
 * A Utility for timing things. this is NOT multi-thread safe.
 **/
public class LapTimer
{
  private long               startTime       = 0;
  private long               lapTime         = 0;
  private long               endTime         = 0;
  private ArrayList<LapTime> lapTimes        = null;
  private String             label           = null;
  private long               pausedTime      = 0;
  private long               pausedTotalTime = 0;
  private static Clock       clock           = new Clock();
  public static void registerClock(Clock clock)
  {
    LapTimer.clock = clock;
  }
  public LapTimer()
  {
    this(null);
  }
  public LapTimer(String label)
  {
    startTime = getCurrentTime();
    lapTime = startTime;
    lapTimes = new ArrayList<LapTime>();
    this.label = label;
  }
  private long getCurrentTime()
  {
    return clock.getTime();
  }
  public String getLabel()
  {
    return label;
  }
  /**
   * Marks the time for a lap and a label.
   * ie. A timer could store - 1 hour 10 mins ["1st Quarter", 15 mins,"2nd Quarter", 15 mins,"Half Time", 10 mins, "3rd Quarter", 15 mins,"4th Quarter", 15 mins] 
   **/
  public long lap(String label)
  {
    return lap(false, label);
  }
  public long end(String label)
  {
    return lap(true, label);
  }
  public boolean isPaused()
  {
    return pausedTime != 0;
  }
  public void pause()
  {
    if (!isPaused())
    {
      pausedTime = getCurrentTime();
    }
  }
  public void resume()
  {
    if (isPaused())
    {
      lapTime = getCurrentTime();
      pausedTotalTime += lapTime - pausedTime;
      pausedTime = 0;
    }
  }
  public long lap(boolean end, String label)
  {
    long newTime = getCurrentTime();
    long difference = newTime - lapTime;
    SimpleLogger.variable("difference", difference);
    lapTimes.add(new LapTime(difference, label));
    lapTime = newTime;
    endTime = end ? newTime : 0;
    return difference;
  }
  public LapTime[] getLapTimes()
  {
    return LapTime.toArray(lapTimes);
  }
  public int getLapCount()
  {
    return lapTimes.size();
  }
  public LapTime getLap(int i)
  {
    if (i >= lapTimes.size())
    { return null; }
    return (LapTime) lapTimes.get(i);
  }
  public LapTime getLap(String label)
  {
    for (int i = 0; i < lapTimes.size(); i++)
    {
      LapTime time = (LapTime) lapTimes.get(i);
      if (label.equals(time.getLabel()))
      { return time; }
    }
    return null;
  }
  public long getTotalTime()
  {
    long lastrecordedTime = 0;
    if (endTime != 0)
    {
      lastrecordedTime = endTime;
    }
    else if (isPaused())
    {
      lastrecordedTime = pausedTime;
    }
    else
    {
      lastrecordedTime = getCurrentTime();
    }
    return lastrecordedTime - startTime - pausedTotalTime;
  }
  public DateDifference getTotalTimeAsDateDifference()
  {
    return new DateDifference(getTotalTime());
  }
  public String toString()
  {
    String value = String.format(
        "com.spun.util.timers.LapTimer[ Label = '%s',\n isPaused = %s ,\n  Lap Times = %s ,\n Total Paused Time = %s ,\n Total Time = %s]",
        label, isPaused(), getPrintableLapTimesArray(), new DateDifference(pausedTotalTime).getStandardTimeText(1),
        getTotalTimeAsDateDifference().getStandardTimeText(1));
    return value;
  }
  /**
   * convenience function for toString().
   **/
  private String getPrintableLapTimesArray()
  {
    LapTime times[] = getLapTimes();
    String output = "[";
    for (int i = 0; i < times.length; i++)
    {
      output += (i > 0) ? ", " : "";
      output += times[i].getLapTimeAsDateDifference().getStandardTimeText(2);
    }
    output += "]";
    return output;
  }
}
