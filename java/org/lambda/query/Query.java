package org.lambda.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.lambda.functions.Function1;
import org.lambda.query.OrderBy.Order;

import com.spun.util.ArrayUtils;

public class Query<In>
{
  public static <In, Out> List<Out> select(List<In> list, Function1<In, Out> function)
  {
    ArrayList<Out> out = new ArrayList<Out>();
    for (In i : list)
    {
      out.add(function.call(i));
    }
    return out;
  }
  public static <In, Out> List<Out> select(In[] list, Function1<In, Out> function)
  {
    return select(ArrayUtils.asList(list), function);
  }
  public static <In> List<In> where(Iterable<In> list, Function1<In, Boolean> funct)
  {
    ArrayList<In> out = new ArrayList<In>();
    for (In i : list)
    {
      if (funct.call(i))
      {
        out.add(i);
      }
    }
    return out;
  }
  public static <In> List<In> where(In[] list, Function1<In, Boolean> funct)
  {
    ArrayList<In> out = new ArrayList<In>();
    for (In i : list)
    {
      if (funct.call(i))
      {
        out.add(i);
      }
    }
    return out;
  }
  public static <In> In max(List<In> list, Function1<In, Comparable> f1)
  {
    return getTop(list, f1, 1);
  }
  public static <In> In min(List<In> list, Function1<In, Comparable> f1)
  {
    return getTop(list, f1, -1);
  }
  public static <In> Double average(List<In> list, Function1<In, Number> f1)
  {
    double total = 0.00;
    for (In in : list)
    {
      total += f1.call(in).doubleValue();
    }
    return total / list.size();
  }
  private static <In> In getTop(List<In> list, Function1<In, Comparable> f1, int modifier)
  {
    if (ArrayUtils.isEmpty(list)) { return null; }
    In found = list.get(0);
    Comparable max = f1.call(found);
    for (In in : list)
    {
      Comparable current = f1.call(in);
      if (max.compareTo(current) * modifier < 0)
      {
        max = current;
        found = in;
      }
    }
    return found;
  }
  public static <T> T[] orderBy(T[] list, Function1<T, Comparable> f1)
  {
    return orderBy(list, Order.Ascending, f1);
  }
  public static <T> T[] orderBy(T[] list, Order order, Function1<T, Comparable> f1)
  {
    Arrays.sort(list, new OrderBy(order, f1));
    return list;
  }
  public static <T> List<T> orderBy(List<T> list, Function1<T, Comparable> f1)
  {
    return orderBy(list, Order.Ascending, f1);
  }
  public static <T> List<T> orderBy(List<T> list, Order order, Function1<T, Comparable> f1)
  {
    Collections.sort(list, new OrderBy(order, f1));
    return list;
  }
  public static <In, Out extends Number> Double sum(In[] list, Function1<In, Out> f1)
  {
    return sum(ArrayUtils.asList(list), f1);
  }
  public static <In, Out extends Number> Double sum(Collection<In> list, Function1<In, Out> f1)
  {
    double sum = 0;
    for (In in : list)
    {
      sum += f1.call(in).doubleValue();
    }
    return sum;
  }
  public static <Out extends Number> Double sum(Collection<Out> list)
  {
    return sum(list, new Echo1<Out>());
  }
  public static <Out extends Number> Double sum(Out[] list)
  {
    return sum(list, new Echo1<Out>());
  }
  public static <T extends Number> T max(List<T> numbers)
  {
    return (T) max((List) numbers, new Echo1<Comparable>());
  }
  public static <T extends Number> T max(T[] numbers)
  {
    return max(ArrayUtils.asList(numbers));
  }
  public static <T extends Number> T min(List<T> numbers)
  {
    return (T) min((List) numbers, new Echo1<Comparable>());
  }
}
