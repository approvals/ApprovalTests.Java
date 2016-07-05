package com.spun.util;

public class Markers implements AutoCloseable
{
  private String text;
  public Markers(String text)
  {
    this.text = text;
    MySystem.markerIn(text);
  }
  @Override
  public void close()
  {
    MySystem.markerOut(text);
  }
}
