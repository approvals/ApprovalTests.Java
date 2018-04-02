package com.spun.util.timers.tests;

import com.spun.util.logger.SimpleLogger;
import com.spun.util.timers.LapTime;
import com.spun.util.timers.LapTimer;

import junit.framework.TestCase;

public class LapTimerTest extends TestCase
{
  public void test()
  {
    try
    {
      MockClock c = new MockClock(1);
      LapTimer.registerClock(c);
      LapTimer timer = new LapTimer("Test Timer");
      c.setTime(3001);
      timer.lap("3");
      timer.pause();
      c.setTime(5001);
      timer.resume();
      c.setTime(9001);
      timer.end("4");
      checkTimer(timer, 7, new int[]{3, 4});
    }
    catch (Exception e)
    {
      SimpleLogger.warning(e);
      fail(e.getMessage());
    }
  }
  public void checkTimer(LapTimer timer, int totalTime, int lapTimes[])
  {
    assertEquals("Total time", totalTime * 1000, timer.getTotalTime());
    LapTime times[] = timer.getLapTimes();
    for (int i = 0; i < times.length; i++)
    {
      assertEquals("Lap[" + i + "]", lapTimes[i] * 1000, times[i].getLapTime());
    }
  }
}
