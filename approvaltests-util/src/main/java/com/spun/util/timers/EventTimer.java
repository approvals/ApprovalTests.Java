package com.spun.util.timers;

import java.util.Date;
import java.util.HashMap;

/**
 * A Utility for timing things. this is multi-thread safe.
 **/
public class EventTimer
{
  private EventTime             time       = null;
  private HashMap<String, Date> startTimes = new HashMap<String, Date>();
  public EventTimer()
  {
    time = new EventTime();
  }
  public EventTimer(String label, long timeLimit)
  {
    time = new EventTime(label, timeLimit);
  }
  public EventTime getEventTime()
  {
    return time;
  }
  public void start()
  {
    startTimes.put("" + Thread.currentThread().hashCode(), new Date());
  }
  public void end()
  {
    Date startTime = (Date) startTimes.remove("" + Thread.currentThread().hashCode());
    if (startTime == null)
    { throw new Error("Tried to end when not started"); }
    time.add(System.currentTimeMillis() - startTime.getTime());
  }
}
