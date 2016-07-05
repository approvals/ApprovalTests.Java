package com.spun.util.logger.tests;

import org.approvaltests.Approvals;
import org.junit.Test;

import com.spun.util.logger.Markers;
import com.spun.util.logger.SimpleLogger;

public class SimpleLoggerTest
{
  @Test
  public void test()
  {
    StringBuffer output = SimpleLogger.logToString();
    try (Markers m = SimpleLogger.useMarkers();)
    {
      try (Markers m2 = SimpleLogger.useMarkers();)
      {
        SimpleLogger.event("Starting Logging");
        String name = "llewellyn";
        SimpleLogger.variable("name", name);
        SimpleLogger.query("Select * from people");
        for (int i = 0; i < 42; i++)
        {
          SimpleLogger.hourGlass();
        }
        SimpleLogger.variable("Numbers", new Integer[]{1, 2, 3, 4, 5});
        SimpleLogger.warning(new Error());
      }
    }
    Approvals.verify(output);
  }
}
