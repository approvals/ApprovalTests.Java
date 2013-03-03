package com.spun.util.timers.tests;

import com.spun.util.timers.Clock;

public class MockClock extends Clock
{
  private long time;
  public MockClock(long time)
  {
    setTime(time);
  }
  @Override
  public long getTime()
  {
    return time;
  }
  public void setTime(long time)
  {
    this.time = time;
  }
}
