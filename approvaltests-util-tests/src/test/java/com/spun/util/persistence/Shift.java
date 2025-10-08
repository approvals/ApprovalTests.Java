package com.spun.util.persistence;

import java.util.Calendar;

public class Shift
{
  private final String name;
  private final String position;
  public String getName()
  {
    return name;
  }

  public String getPosition()
  {
    return position;
  }

  public Calendar getStartTime()
  {
    return startTime;
  }
  private final Calendar startTime;
  public Shift(String name, String position, Calendar startTime)
  {
    this.name = name;
    this.position = position;
    this.startTime = startTime;
  }
}
