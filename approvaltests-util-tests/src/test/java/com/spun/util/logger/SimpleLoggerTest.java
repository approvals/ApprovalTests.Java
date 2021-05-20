package com.spun.util.logger;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

public class SimpleLoggerTest
{
  @Test
  public void test()
  {
    // begin-snippet: log_to_string
    StringBuffer output = SimpleLogger.logToString();
    // end-snippet
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
  public void sample()
  {
    // begin-snippet: log_nothing
    SimpleLogger.logToNothing();
    // end-snippet 
  }
}
