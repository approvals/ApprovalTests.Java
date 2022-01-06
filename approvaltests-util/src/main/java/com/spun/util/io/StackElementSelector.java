package com.spun.util.io;

public interface StackElementSelector
{
  public StackTraceElement selectElement(StackTraceElement[] trace);
  public void increment();
}