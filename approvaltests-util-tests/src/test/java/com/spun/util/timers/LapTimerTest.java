package com.spun.util.timers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.spun.util.logger.SimpleLogger;
import com.spun.util.timers.test.MockClock;
import org.junit.jupiter.api.Test;

public class LapTimerTest
{
  @Test
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
    assertEquals(totalTime * 1000L, (Object) timer.getTotalTime(), "Total time");
    LapTime times[] = timer.getLapTimes();
    for (int i = 0; i < times.length; i++)
    {
      assertEquals(lapTimes[i] * 1000L, (Object) times[i].getLapTime(), "Lap[" + i + "]");
    }
  }
}
