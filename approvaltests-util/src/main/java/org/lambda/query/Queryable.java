package org.lambda.query;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lambda.Extendable;
import org.lambda.functions.Function1;
import org.lambda.query.OrderBy.Order;

import com.spun.util.ObjectUtils;

public class Queryable<In> extends ArrayList<In>
{
  // TODO: autogenerate this;
  private final long serialVersionUID = 1L;
  public <T extends Extendable<List<In>>> T use(Class<T> that)
  {
    try
    {
      T t = that.newInstance();
      t.setCaller(this);
      return t;
    }
    catch (Throwable t)
    {
      throw ObjectUtils.throwAsError(t);
    }
  }
  public <Out> Queryable<Out> select(Function1<In, Out> function)
  {
    return Query.select(this, function);
  }
  public Queryable<In> where(Function1<In, Boolean> funct)
  {
    return Query.where(this, funct);
  }
  public In first()
  {
    return this.isEmpty() ? null : this.get(0);
  }
  public In first(Function1<In, Boolean> filter)
  {
    return Query.first(this, filter);
  }
  public <Out extends Comparable<Out>> In max(Function1<In, Out> f1)
  {
    return Query.max(this, f1);
  }
  public <Out extends Comparable<Out>> In min(Function1<In, Out> f1)
  {
    return Query.min(this, f1);
  }
  public Double average(Function1<In, Number> f1)
  {
    return Query.average(this, f1);
  }
  public Queryable<In> orderBy(Function1<In, Comparable<?>> f1)
  {
    return Query.orderBy(this, f1);
  }
  public Queryable<In> orderBy(Order order, Function1<In, Comparable<?>> f1)
  {
    return Query.orderBy(this, order, f1);
  }
  public <Out extends Number> Double sum(Function1<In, Out> f1)
  {
    return Query.sum(this, f1);
  }
  public Double sum()
  {
    return Query.sum((List<Number>) this);
  }
  public In max()
  {
    return (In) Query.max((List<Number>) this);
  }
  public In min()
  {
    return (In) Query.min((List<Number>) this);
  }
  public boolean any(Function1<In, Boolean> funct)
  {
    return Query.any(this, funct);
  }
  public static <T> Queryable<T> as(List<T> list)
  {
    if (list instanceof Queryable)
    { return (Queryable<T>) list; }
    Queryable<T> q = new Queryable<T>();
    q.addAll(list);
    return q;
  }
  public static <T> Queryable<T> as(T... array)
  {
    return as(Arrays.asList(array));
  }
  /**
   * Maintains order
   */
  public Queryable<In> distinct()
  {
    return Query.distinct(this);
  }
  public In[] asArray()
  {
    QueryableWithType<In> withType = new QueryableWithType<>(this, (Class<In>) this.get(0).getClass());
    return withType.asArray();
  }
  static class QueryableWithType<T> extends Queryable<T>
  {
    private final Class<T> type;
    public QueryableWithType(List<T> list, Class<T> type)
    {
      this.addAll(list);
      this.type = type;
    }
    @Override
    public T[] asArray()
    {
      int size = this.size();
      T[] result = (T[]) Array.newInstance(type, size);
      return toArray(result);
    }
  }
}
