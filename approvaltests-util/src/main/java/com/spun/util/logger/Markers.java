package com.spun.util.logger;

public class Markers implements AutoCloseable
{
  private SimpleLoggerInstance log;
  private String text;
  public Markers(SimpleLoggerInstance log, String text)
  {
    this.log = log;
    this.text = text;
    log.markerIn(text);
  }
  @Override
  public void close()
  {
    log.markerOut(text);
  }
}
