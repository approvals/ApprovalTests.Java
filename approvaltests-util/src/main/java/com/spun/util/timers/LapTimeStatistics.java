package com.spun.util.timers;

/**
 * A Utility for timing things. this is multi-thread safe.
 **/
public class LapTimeStatistics
{
  private String    label     = null;
  private EventTime totalTime = null;
  private EventTime times[]   = null;
  public LapTimeStatistics(LapTimer lapTimer)
  {
    loadFirstLapTimer(lapTimer);
  }
  public LapTimeStatistics(String label)
  {
    this.label = label;
  }
  public int getCount()
  {
    return (totalTime == null) ? 0 : totalTime.getCount();
  }
  private void loadFirstLapTimer(LapTimer lapTimer)
  {
    if (label == null)
    {
      lapTimer.getLabel();
    }
    totalTime = new EventTime("Total Time", 0);
    totalTime.add(lapTimer.getTotalTime());
    LapTime lapTimes[] = lapTimer.getLapTimes();
    times = new EventTime[lapTimes.length];
    for (int i = 0; i < lapTimes.length; i++)
    {
      times[i] = new EventTime(lapTimes[i].getLabel(), 0);
      times[i].add(lapTimes[i].getLapTime());
    }
  }
  public EventTime getTotalTime()
  {
    return totalTime;
  }
  public String getLabel()
  {
    return label;
  }
  public synchronized void add(LapTimer lapTimer)
  {
    if (totalTime == null)
    {
      loadFirstLapTimer(lapTimer);
    }
    else
    {
      totalTime.add(lapTimer.getTotalTime());
      LapTime lapTimes[] = lapTimer.getLapTimes();
      if (lapTimes.length != times.length)
      { throw new Error("Tried to add a LapTimer with " + lapTimes.length + " laps. Must have " + times.length); }
      for (int i = 0; i < lapTimes.length; i++)
      {
        times[i].add(lapTimes[i].getLapTime());
      }
    }
  }
  public EventTime[] getLapTimes()
  {
    return this.times;
  }
}
