package com.spun.util.logger;

import org.approvaltests.Approvals;
import org.approvaltests.utils.logger.SimpleLoggerApprovals;
import org.approvaltests.utils.logger.VerifySimpleLogger;
import org.junit.jupiter.api.Test;

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

  @Test
  void testMarkers()
  {
    // begin-snippet: log_to_string
    StringBuffer log = SimpleLogger.logToString();
    new Sample().methodThatLogs();
    Approvals.verify(log);
    // end-snippet
  }

  @Test
  void testToggles()
  {
    // begin-snippet: simple_logger_toggles
    SimpleLogger.get().marker = true;
    SimpleLogger.get().event = false;
    SimpleLogger.get().variable = false;
    SimpleLogger.get().query = true;
    // end-snippet
  }

  @Test
  void testVariable()
  {
    try (VerifySimpleLogger verify = SimpleLoggerApprovals.verify())
    {
      SimpleLogger.variable("name", "Scott");
      SimpleLogger.variable("name", "Scott", true);
    }
  }

  public void sample()
  {
    // begin-snippet: log_nothing
    SimpleLogger.logToNothing();
    // end-snippet
  }
}
