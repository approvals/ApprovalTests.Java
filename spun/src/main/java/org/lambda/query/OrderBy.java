package org.lambda.query;

import java.util.Comparator;

import org.lambda.functions.Function1;

public class OrderBy<T, Out extends Comparable<?>> implements Comparator<T>
{
  public static enum Order {
                            Ascending, Descending
  }
  private Function1<T, Out> f1;
  private int               ascending;
  public OrderBy(Function1<T, Out> f1)
  {
    this(Order.Ascending, f1);
  }
  public OrderBy(Order order, Function1<T, Out> f1)
  {
    this.f1 = f1;
    this.ascending = order == Order.Ascending ? 1 : -1;
  }
  public static <T, Out extends Comparable<Out>> OrderBy<T, Out> ascending(Function1<T, Out> f1)
  {
    return new OrderBy<>(Order.Ascending, f1);
  }
  public static <T, Out extends Comparable<Out>> OrderBy<T, Out> descending(Function1<T, Out> f1)
  {
    return new OrderBy<>(Order.Descending, f1);
  }
  @Override
  public int compare(T a, T b)
  {
    Comparable<Object> v1 = (Comparable<Object>) f1.call(a);
    Out v2 = f1.call(b);
    if (v1 == null || v2 == null) { return compareNull(v1, v2); }
    return v1.compareTo((Object) v2) * ascending;
  }
  public static int compareNull(Object o1, Object o2)
  {
    if (o1 == o2) { return 0; }
    return (o1 == null) ? -1 : 1;
  }
}
