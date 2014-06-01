package com.spun.util;

public class Tuple<H, K>
{
  private final H first;
  private final K second;
  public Tuple(H first, K second)
  {
    this.first = first;
    this.second = second;
  }
  public H getFirst()
  {
    return first;
  }
  public K getSecond()
  {
    return second;
  }
  @Override
  public String toString()
  {
    return String.format("<%s,%s>", first, second);
  }
}
