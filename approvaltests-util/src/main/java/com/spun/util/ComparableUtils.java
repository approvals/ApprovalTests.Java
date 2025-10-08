package com.spun.util;

public class ComparableUtils
{
  public static <T extends Comparable<T>> boolean isAEqualToB(T a, T b)
  {
    return a.compareTo(b) == 0;
  }

  public static <T extends Comparable<T>> boolean isALessThanOrEqualToB(T a, T b)
  {
    return a.compareTo(b) <= 0;
  }

  public static <T extends Comparable<T>> boolean isALessThanB(T a, T b)
  {
    return a.compareTo(b) < 0;
  }

  public static <T extends Comparable<T>> ComparableWrapper<T> wrap(T object)
  {
    return new ComparableWrapper<>(object);
  }
  public static class ComparableWrapper<T extends Comparable<T>>
  {
    private final T object;
    public ComparableWrapper(T object)
    {
      this.object = object;
    }

    public boolean isLessThan(T other)
    {
      return isALessThanB(object, other);
    }

    public boolean isEqualTo(T other)
    {
      return isAEqualToB(object, other);
    }

    public boolean isLessThanOrEqual(T other)
    {
      return isALessThanOrEqualToB(object, other);
    }
  }
}
