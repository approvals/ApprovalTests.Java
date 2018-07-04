package com.spun.util.io;

public interface StackElementSelector
{
  public abstract StackTraceElement selectElement(StackTraceElement[] trace) throws Exception;
  public abstract void increment();
}