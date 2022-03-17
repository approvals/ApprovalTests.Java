package com.spun.util.timers;

import com.spun.util.DateDifference;

/**
 * A Utility for timing things. this is multi-thread safe.
 **/
public class LapTime
{
  private long   lapTime = 0;
  private String label   = null;
  public LapTime(long lapTime, String label)
  {
    this.label = label;
    this.lapTime = lapTime;
  }
  public long getLapTime()
  {
    return lapTime;
  }
  public String getLabel()
  {
    return label;
  }
  public DateDifference getLapTimeAsDateDifference()
  {
    return new DateDifference(lapTime);
  }
  /**
   * A convenience function to turn a ArrayList of LapTime objects into an Array
   * of the LapTime objects.
   * 
   * @param ArrayListOf
   *          a ArrayList of LapTime objects
   * @return the array of LapTime.
   * @throws Error
   *           if an element of ArrayListOf is not a LapTime object.
   **/
  public static LapTime[] toArray(java.util.ArrayList<LapTime> ArrayListOf)
  {
    if (ArrayListOf == null)
    { return new LapTime[0]; }
    LapTime[] array = new LapTime[ArrayListOf.size()];
    for (int i = 0; i < array.length; i++)
    {
      java.lang.Object rowObject = ArrayListOf.get(i);
      if (rowObject instanceof LapTime)
      {
        array[i] = (LapTime) rowObject;
      }
      else
      {
        throw new Error("toArray[i] is not an instance of LapTime but a " + rowObject.getClass().getName());
      }
    }
    return array;
  }
  public String toString()
  {
    String value = "com.spun.util.timers.LapTime[";
    value += " Label = '" + label + "'" + ",\n" + " Lap Time = "
        + getLapTimeAsDateDifference().getStandardTimeText(1) + "]";
    return value;
  }
}
