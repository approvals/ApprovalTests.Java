package org.lambda.query;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.lambda.Extendable;
import org.lambda.functions.Function1;
import org.lambda.query.OrderBy.Order;

import com.spun.util.ClassUtils;
import com.spun.util.ObjectUtils;

public class Queryable<In> extends ArrayList<In>
{
  // TODO: autogenerate this;
  private final long serialVersionUID = 1L;
  private Class<In>  type;
  public Queryable()
  {
    this(null);
  }
  public Queryable(Class<In> type)
  {
    this.type = type;
  }
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
  public boolean all(Function1<In, Boolean> filter)
  {
    return Query.all(this, filter);
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
  /**
   * Why does sum() return double? see {@link org.lambda.query.Query#sum(Number[])}
   */
  public <Out extends Number> Double sum(Function1<In, Out> f1)
  {
    return Query.sum(this, f1);
  }
  /**
   * Why does sum() return double? see {@link org.lambda.query.Query#sum(Number[])}
   */
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
    Class<?> type = ClassUtils.getGreatestCommonBaseType(list);
    return Queryable.as(list, (Class<T>) type);
  }
  public static <T> Queryable<T> as(List<T> list, Class<T> type)
  {
    if (list instanceof Queryable)
    { return (Queryable<T>) list; }
    Queryable<T> q = new Queryable<T>(type);
    q.addAll(list);
    return q;
  }
  public static <T> Queryable<T> as(T... array)
  {
    return as(Arrays.asList(array), (Class<T>) array.getClass().getComponentType());
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
    int size = this.size();
    In[] result = (In[]) Array.newInstance(getType(), size);
    return toArray(result);
  }
  public Class<In> getType()
  {
    if (type == null)
    {
      type = (Class<In>) ClassUtils.getGreatestCommonBaseType(this);
    }
    return type;
  }

  public <Out> Queryable<Out> selectMany(Function1<In, Collection<Out>> selector) {
    return Query.selectMany(this, selector);
  }
}
