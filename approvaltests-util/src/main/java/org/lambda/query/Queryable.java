package org.lambda.query;

import com.spun.util.ClassUtils;
import com.spun.util.ObjectUtils;
import org.lambda.Extendable;
import org.lambda.actions.Action0;
import org.lambda.functions.Function0;
import org.lambda.functions.Function1;
import org.lambda.query.OrderBy.Order;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
  public static <In> Queryable<In> createEmpty(In[] list)
  {
    if (list == null)
    {
      return new Queryable<>();
    }
    else
    {
      return new Queryable(list.getClass().getComponentType());
    }
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
    return firstOrDefault(null);
  }
  public In first(Function1<In, Boolean> filter)
  {
    return Query.first(this, filter);
  }
  public <E extends Throwable> In firstOrThrow(Function1<In, Boolean> filter, Function0<E> exception) throws E {
    return Query.firstOrThrow(this, filter, exception);
  }
  public In firstOrDefault(In defaultValue)
  {
    return this.isEmpty() ? defaultValue : this.get(0);
  }
  public Action0 firstOrThrow(In i, Object o) {
    return null;
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
  public static <T> Queryable<T> of(List<T> list)
  {
    return as(list);
  }
  public static <T> Queryable<T> of(List<T> list, Class<T> type)
  {
    return as(list, type);
  }
  public static <T> Queryable<T> of(Set<T> set)
  {
    return as(set);
  }
  public static <T> Queryable<T> of(Set<T> set, Class<T> type)
  {
    return as(set, type);
  }
  public static <T> Queryable<T> of(T... array)
  {
    return as(array);
  }
  public static <T> Queryable<T> of(Stream<T> stream)
  {
    return as(stream);
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
  public static <T> Queryable<T> as(Set<T> set)
  {
    return as(new ArrayList<>(set));
  }
  public static <T> Queryable<T> as(Set<T> set, Class<T> type)
  {
    return as(new ArrayList<>(set), type);
  }
  public static <T> Queryable<T> as(T... array)
  {
    return as(Arrays.asList(array), (Class<T>) array.getClass().getComponentType());
  }
  public static <T> Queryable<T> as(Stream<T> stream)
  {
    return Queryable.as(stream.collect(Collectors.toList()));
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
  public <Out> Queryable<Out> selectMany(Function1<In, Collection<Out>> selector)
  {
    return Query.selectMany(this, selector);
  }
  public <Out> Queryable<Out> selectManyArray(Function1<In, Out[]> selector)
  {
    return Query.selectManyArray(this, selector);
  }
  public <Key> Queryable<Entry<Key, Queryable<In>>> groupBy(Function1<In, Key> keySelector)
  {
    return Query.groupBy(this, keySelector);
  }
  public <Key, Out1, Out2> Queryable<Entry<Key, Out2>> groupBy(Function1<In, Key> keySelector,
      Function1<In, Out1> valueSelector, Function1<Queryable<Out1>, Out2> resultSelector)
  {
    return Query.groupBy(this, keySelector, valueSelector, resultSelector);
  }
  public String join(String joinCharacter)
  {
    return String.join(joinCharacter, this.select(t -> "" + t));
  }
  public <Out> String join(String joinCharacter, Function1<In, Out> transformer)
  {
    return String.join(joinCharacter, this.select(t -> "" + transformer.call(t)));
  }
  public Queryable<Queryable<In>> split(Function1<In, Boolean> function)
  {
    Queryable<Queryable<In>> results = new Queryable<>();
    Queryable<In> part = new Queryable<>();
    for (In in : this)
    {
      if (function.call(in))
      {
        results.add(part);
        part = new Queryable<>();
      }
      else
      {
        part.add(in);
      }
    }
    return results;
  }
  public Queryable<In> skip(int number)
  {
    return Query.skip(this, number);
  }
  public Queryable<In> take(int number)
  {
    return Query.take(this, number);
  }
  public Queryable<In> combine(Queryable<In> iterables)
  {
    Queryable<In> queryable = new Queryable<>(this.type);
    queryable.addAll(this);
    queryable.addAll(iterables);
    return queryable;
  }
  public Queryable<In> selectRecursivelyUntil(Function1<In, In> selector, Function1<In, Boolean> until)
  {
    return Query.selectRecursivelyUntil(this, selector, until);
  }
  public In last()
  {
    return Query.last(this);
  }
}
