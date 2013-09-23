package com.spun.util;

import java.util.Random;

public class StringIdentifierGenerator
{
  private static final long MAX_RANDOM_LEN       = 2176782336L; // 36 ** 6
  private static final long MAX_TIME_SECTION_LEN = 46656L;      // 36 ** 3
  private static final long TIC_DIFFERENCE       = 2000;
  private static final int  RANDOM_LENGTH        = 6;
  private static final int  TIME_LENGTH          = 3;
  private static int        counter              = 0;
  private static long       lastTimeValue        = 0;
  private static Random     randomizer           = new Random();
  public static synchronized String nextIdentifier()
  {
    long currentRandom = randomizer.nextLong();
    if (currentRandom < 0)
    {
      currentRandom = -currentRandom;
    }
    currentRandom %= MAX_RANDOM_LEN;
    currentRandom += MAX_RANDOM_LEN;
    long currentTimeValue = 0;
    int currentCount = 0;
    currentTimeValue = (System.currentTimeMillis() / TIC_DIFFERENCE);
    currentTimeValue %= MAX_TIME_SECTION_LEN;
    currentTimeValue += MAX_TIME_SECTION_LEN;
    if (lastTimeValue != currentTimeValue)
    {
      lastTimeValue = currentTimeValue;
      counter = 0;
    }
    currentCount = counter++;
    StringBuffer id = new StringBuffer(15);
    id.append(Long.toString(currentRandom, 36).substring(1));
    id.append(Long.toString(currentTimeValue, 36).substring(1));
    id.append(Long.toString(currentCount, 36));
    return id.toString();
  }
}
