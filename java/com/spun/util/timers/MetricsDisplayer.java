package com.spun.util.timers;

import org.apache.velocity.context.Context;
import com.spun.util.velocity.ContextAware;
import com.spun.util.velocity.VelocityParser;

/**
 * A Utility for timing things. this is multi-thread safe.
 **/
public class MetricsDisplayer implements ContextAware
{
  public static String      TEMPLATE = "C:\\metrics_template.htm";
  private LapTimeStatistics stats    = null;
  /************************************************************************/
  public MetricsDisplayer(LapTimeStatistics stats)
  {
    this.stats = stats;
  }
  /************************************************************************/
  public String saveToFile(String outputFileName)
  {
    return VelocityParser.parseFile(TEMPLATE, outputFileName, this);
  }
  /***********************************************************************/
  public LapTimeStatistics getStatistics()
  {
    return stats;
  }
  /***********************************************************************/
  public void setupContext(Context context)
  {
    context.put("stats", stats);
  }
  /***********************************************************************/
  /***********************************************************************/
}