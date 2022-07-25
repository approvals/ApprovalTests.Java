package com.spun.util.parser;

import java.util.Arrays;

public class SteppingIterator
{
  public static final int[] DEFAULT_STEPPING = {1};
  private int               offset           = 0;
  private int[]             stepping         = DEFAULT_STEPPING;
  private int               actualSize       = 0;
  public SteppingIterator(int offset, int[] stepping, int actualSize)
  {
    this.offset = offset;
    this.stepping = stepping;
    this.actualSize = actualSize;
    assertSteppingValid(stepping);
  }
  private void assertSteppingValid(int[] stepping)
  {
    if (stepping == null || stepping.length == 0 || stepping[0] == 0)
    { throw new NullPointerException("Stepping cannot be null or zero in " + Arrays.toString(stepping)); }
    for (int i = stepping.length - 1; i > 0; i--)
    {
      if (stepping[i - 1] % stepping[i] != 0)
      {
        String string = String.format("Invalid stepping parameters - %s is not divisible by %s in parameters %s",
            stepping[i - 1], stepping[i], Arrays.toString(stepping));
        throw new Error(string);
      }
    }
  }
  public boolean isLast(int currentPlace, int forSteppingLevel)
  {
    if (forSteppingLevel == -1 || stepping[forSteppingLevel] == 1)
    {
      return (currentPlace + 1) == getSize(true, true);
    }
    else
    {
      return (((currentPlace + 1) % stepping[forSteppingLevel]) == 0);
    }
  }
  public boolean isFirst(int index, int forSteppingLevel)
  {
    return (getStepPositionForRound(forSteppingLevel, 0, index) == 0);
  }
  public int getSize(boolean includeOffset, boolean includeStepping)
  {
    int size = actualSize;
    size += includeOffset ? offset : 0;
    if (includeStepping && (size % stepping[0]) != 0)
    {
      size += (stepping[0] - (size % stepping[0]));
    }
    return size;
  }
  /**
   * @return the index of the current Step.
   **/
  public static int getStepCountForRound(int forSteppingLevel, int onIndex, int[] stepping, int indexBase)
  {
    if (forSteppingLevel == -1 || stepping[forSteppingLevel] == 1)
    {
      return indexBase;
    }
    else
    {
      return ((onIndex / stepping[forSteppingLevel]) + indexBase);
    }
  }
  public int getStepCountForRound(int forSteppingLevel, int currentPlace, int indexBase)
  {
    return getStepCountForRound(forSteppingLevel, currentPlace, this.stepping, indexBase);
  }
  public int getTotalStepCountForRound(int forSteppingLevel, int indexBase)
  {
    return getStepCountForRound(forSteppingLevel, getSize(true, true) - 1, stepping, indexBase);
  }
  public int getStepPositionForRound(int forSteppingLevel, int indexBase, int currentIndex)
  {
    if (forSteppingLevel == -1 || stepping[forSteppingLevel] == 1)
    {
      return currentIndex + indexBase;
    }
    else
    {
      return ((currentIndex % stepping[forSteppingLevel]) + indexBase);
    }
  }
  public int getActualPosition(int position)
  {
    position = position - this.offset;
    return (position < 0 || this.actualSize <= position) ? -1 : position;
  }
}
