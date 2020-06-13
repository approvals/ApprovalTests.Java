package com.spun.util;

import com.spun.util.logger.SimpleLogger;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * A static class of convenience functions for Manipulating numbers
 **/
public class NumberUtils
{
  public static Random RANDOM = new Random();
  public static int getMax(int value1, int value2)
  {
    return value1 > value2 ? value1 : value2;
  }
  /**
   * Loads an int from a String.
   **/
  public static int load(String i, int defaultValue)
  {
    return load(i, defaultValue, true);
  }
  /**
   * Loads an int from a String.
   **/
  public static int load(String i, int defaultValue, boolean stripNonNumeric)
  {
    try
    {
      i = stripNonNumeric ? StringUtils.stripNonNumeric(i, true, true) : i;
      defaultValue = Integer.parseInt(i);
    }
    catch (Exception e)
    {
    }
    return defaultValue;
  }
  /**
   * Loads an int from a String.
   **/
  public static long load(String i, long defaultValue)
  {
    try
    {
      defaultValue = Long.parseLong(i);
    }
    catch (Exception e)
    {
    }
    return defaultValue;
  }
  public static double load(String i, double defaultValue)
  {
    try
    {
      i = StringUtils.stripNonNumeric(i, true, true);
      defaultValue = Double.parseDouble(i);
    }
    catch (Exception e)
    {
    }
    return defaultValue;
  }
  public static boolean load(String i, boolean d)
  {
    return (i == null) ? d : "true".equalsIgnoreCase(i);
  }
  public static int[] loadArray(String summaryString, String seperator, int defaultWhenLoading)
  {
    String parts[] = StringUtils.split(summaryString, seperator);
    int integers[] = new int[parts.length];
    for (int i = 0; i < parts.length; i++)
    {
      integers[i] = load(parts[i], defaultWhenLoading);
    }
    return integers;
  }
  public static double setSignificantDigit(double onNumber, int digit)
  {
    double power = Math.pow(10, digit);
    onNumber = onNumber * power;
    onNumber = Math.round(onNumber);
    onNumber = onNumber / power;
    return onNumber;
  }
  /**
   * a unit test of sorts
   **/
  public static void main(String args[])
  {
    Random r = new Random();
    for (int i = 0; i < 20; i++)
    {
      double d = r.nextDouble();
      int s = r.nextInt(5);
      SimpleLogger.event(d + " , " + s + " -> " + setSignificantDigit(d, s));
    }
  }
  public static boolean doRandomPercentage(int i)
  {
    return RANDOM.nextInt(100) < i;
  }
  public static boolean equals(double one, double two, double delta)
  {
    double actualDelta = one - two;
    return (-delta < actualDelta) && (actualDelta < delta);
  }
  public static Integer[] wrapIntegers(int[] ints)
  {
    Integer[] integers = new Integer[ints.length];
    for (int i = 0; i < ints.length; i++)
    {
      integers[i] = ints[i];
    }
    return integers;
  }
  public static String createRandomStringOfNumbers(int digits)
  {
    StringBuffer buffer = new StringBuffer(digits);
    for (int i = 0; i < digits; i++)
    {
      buffer.append(RANDOM.nextInt(10));
    }
    return buffer.toString();
  }
  public static boolean isIn(int check, int[] available)
  {
    for (int i = 0; i < available.length; i++)
    {
      if (check == available[i])
      { return true; }
    }
    return false;
  }
  public static boolean isEven(int number)
  {
    return ((number % 2) == 0);
  }
  public static <T> T[] getShuffled(T[] objects, int numberToReturn)
  {
    Shuffler[] shuffles = new Shuffler[objects.length];
    for (int i = 0; i < objects.length; i++)
    {
      shuffles[i] = new Shuffler(i);
    }
    Arrays.sort(shuffles, new Shuffler(0));
    ArrayList<T> list = new ArrayList<T>(numberToReturn);
    for (int i = 0; i < numberToReturn; i++)
    {
      list.add(objects[shuffles[i].oldPosition]);
    }
    Object[] objects2 = null;
    try
    {
      objects2 = (Object[]) Array.newInstance(objects.getClass().getComponentType(), numberToReturn);
    }
    catch (Exception e)
    {
      SimpleLogger.warning(e);
      objects2 = new Object[numberToReturn];
    }
    return (T[]) list.toArray(objects2);
  }
  /**
   * randomly chooses a number between the minimum and maximum
   * <div><b>Example:</b> {@code int grade =  NumberUtils.getRandomInt(1,100);} </div>
   * 
   * @param minimum
   *          The lowest possible value (inclusive)
   * @param maximum
   *          The highest possible value (inclusive)
   * @return the random number
   */
  public static int getRandomInt(int minimum, int maximum)
  {
    int diff = maximum - minimum;
    if (diff == 0)
    {
      return maximum;
    }
    else
    {
      return RANDOM.nextInt(diff) + minimum;
    }
  }
  public static int floor(double i)
  {
    return (int) Math.floor(i);
  }
  public static int getNumberOfDigits(int number)
  {
    return ("" + number).length();
  }
  public static double convertDoubleToPercentage(double doub)
  {
    return doub * 100;
  }
  public static double convertPercentageToDouble(double percent)
  {
    return percent / 100;
  }
  /* INNER CLASS */
  public static class Shuffler implements java.util.Comparator<Shuffler>, Serializable
  {
    private static final long serialVersionUID = 1L;
    public int                oldPosition      = 0;
    public int                newPosition      = 0;
    public Shuffler(int oldPosition)
    {
      this.oldPosition = oldPosition;
      this.newPosition = RANDOM.nextInt();
    }
    public int compare(Shuffler s1, Shuffler s2)
    {
      return Double.compare(s1.newPosition, (s2).newPosition);
    }
  }
  public static IntStream toIntStream(int[] numbers)
  {
    return IntStream.of(numbers);
  }
  public static IntStream toIntStream(Integer[] numbers)
  {
    return Arrays.stream(numbers).mapToInt(Integer::intValue);
  }
  public static IntStream toIntStream(List<Integer> numbers)
  {
    return numbers.stream().mapToInt(Integer::intValue);
  }
  public static LongStream toLongStream(long[] numbers)
  {
    return LongStream.of(numbers);
  }
  public static LongStream toLongStream(Long[] numbers)
  {
    return Arrays.stream(numbers).mapToLong(Long::longValue);
  }
  public static LongStream toLongStream(List<Long> numbers)
  {
    return numbers.stream().mapToLong(Long::longValue);
  }
  public static DoubleStream toDoubleStream(double[] numbers)
  {
    return DoubleStream.of(numbers);
  }
  public static DoubleStream toDoubleStream(Double[] numbers)
  {
    return Arrays.stream(numbers).mapToDouble(Double::doubleValue);
  }
  public static DoubleStream toDoubleStream(List<Double> numbers)
  {
    return numbers.stream().mapToDouble(Double::doubleValue);
  }
}