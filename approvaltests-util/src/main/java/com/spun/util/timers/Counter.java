package com.spun.util.timers;

import com.spun.util.DateDifference;

/**
 * A Utility for counting things.
 **/
public class Counter
{
  private int    count     = 0;
  private long   lastTime  = 0;
  private long   startTime = 0;
  private String label     = null;

  public Counter()
  {
    this(null);
  }

  public Counter(String label)
  {
    this.label = label;
    this.startTime = System.currentTimeMillis();
    this.lastTime = this.startTime;
  }

  public void inc()
  {
    count++;
    lastTime = System.currentTimeMillis();
  }

  public void reset()
  {
    count = 0;
    lastTime = System.currentTimeMillis();
  }

  public int getCount()
  {
    return count;
  }

  public long getLastTime()
  {
    return lastTime;
  }

  public int getAverageClicksPerTime(long timeInMilli)
  {
    return (int) (getCount() / ((double) getTimeSinceStart() / timeInMilli));
  }

  public long getTimeSinceLast()
  {
    return System.currentTimeMillis() - lastTime;
  }

  public long getTimeSinceStart()
  {
    return System.currentTimeMillis() - startTime;
  }

  public DateDifference getLastTimeDifference()
  {
    return new DateDifference(System.currentTimeMillis() - lastTime);
  }

  public String toString()
  {
    return "Counter [Count, Time] = [" + count + ", " + getLastTimeDifference().getStandardTimeText(2) + "]";
  }

  /**
   * A convenience function to turn a vector of Counter objects
   * into an Array of the Counter objects.
   * @param vectorOf a Vector of Counter objects
   * @return the array of Counter.
   * @throws Error if an element of vectorOf is not a Counter object.
   **/
  public static Counter[] toArray(java.util.Vector vectorOf)
  {
    if (vectorOf == null) { return new Counter[0]; }
    Counter array[] = new Counter[vectorOf.size()];
    for (int i = 0; i < array.length; i++)
    {
      java.lang.Object rowObject = vectorOf.elementAt(i);
      if (rowObject instanceof Counter)
      {
        array[i] = (Counter) rowObject;
      }
      else
      {
        throw new Error("toArray[i] is not an instance of Counter but a " + rowObject.getClass().getName());
      }
    }
    return array;
  }


  public String getLabel()
  {
    return label;
  }
}