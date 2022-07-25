package com.spun.util;

/**
 * A Simple class looking at memory.
 * @author Llewellyn
 * @version 1.0
 **/
public class Memory
{
  private long                   freeMemory  = 0;
  private long                   totalMemory = 0;
  private long                   usedMemory  = 0;
  private java.text.NumberFormat format      = java.text.NumberFormat.getNumberInstance();
  /**
   * Dumps the current memory status [total, used, free].
   * This forces garbage collection to run first.
   **/
  public Memory()
  {
    System.gc();
    freeMemory = Runtime.getRuntime().freeMemory();
    totalMemory = Runtime.getRuntime().totalMemory();
    usedMemory = totalMemory - freeMemory;
  }
  public String getUsedMemory()
  {
    return format.format(((double) usedMemory) / 1000000);
  }
  public String getFreeMemory()
  {
    return format.format(((double) freeMemory) / 1000000);
  }
  public String getTotalMemory()
  {
    return format.format(((double) totalMemory) / 1000000);
  }
}