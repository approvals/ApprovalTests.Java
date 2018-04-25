package com.spun.util.logger;

@SuppressWarnings("deprecation")
public class Markers implements AutoCloseable
{
  private String text;
  public Markers(String text)
  {
    this.text = text;
    SimpleLogger.markerIn(text);
  }
  @Override
  public void close()
  {
    SimpleLogger.markerOut(text);
  }
}
