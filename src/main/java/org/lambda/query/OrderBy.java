package org.lambda.query;

import java.util.Comparator;

import org.lambda.functions.Function1;

@SuppressWarnings("rawtypes")
public class OrderBy<T> implements Comparator<T>
{
  public static enum Order {
    Ascending, Descending
  }
  private Function1<T, Comparable> f1;
  private int               ascending;
  public OrderBy(Function1<T, Comparable> f1)
  {
    this(Order.Ascending, f1);
  }
  public OrderBy(Order order, Function1<T, Comparable> f1)
  {
    this.f1 = f1;
    this.ascending = order == Order.Ascending ? 1 : -1;
  }
  @Override
  public int compare(T a, T b)
  {
    return f1.call(a).compareTo(f1.call(b)) * ascending;
  }
}
