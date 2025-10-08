package com.spun.util.logger;

public class Sample
{
  // begin-snippet: simple_logger_use_markers
  public void methodThatLogs()
  {
    try (Markers markers = SimpleLogger.useMarkers())
    {
      for (int i = 0; i < 3; i++)
      {
        innerMethod(i);
      }
    }
  }

  private void innerMethod(int i)
  {
    try (Markers markers = SimpleLogger.useMarkers())
    {
      SimpleLogger.variable("i", i);
    }
  }
  // end-snippet
}
