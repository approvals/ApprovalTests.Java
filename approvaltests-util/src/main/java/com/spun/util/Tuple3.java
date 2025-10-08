package com.spun.util;

public class Tuple3<H, K, L>
{
  private final H first;
  private final K second;
  private final L third;
  public Tuple3(H first, K second, L third)
  {
    this.first = first;
    this.second = second;
    this.third = third;
  }

  public H getFirst()
  {
    return first;
  }

  public K getSecond()
  {
    return second;
  }

  public L getThird()
  {
    return third;
  }

  @Override
  public String toString()
  {
    return String.format("<%s,%s,%s>", first, second, third);
  }
}
