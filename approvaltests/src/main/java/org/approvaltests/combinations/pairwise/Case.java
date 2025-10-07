package org.approvaltests.combinations.pairwise;

import org.lambda.query.Query;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class Case
{
  private final LinkedHashMap<String, Object> map    = new LinkedHashMap<>();
  private static Random                       random = new Random(5);
  public static Object random(Object[] array)
  {
    return array[random.nextInt(array.length)];
  }

  private Case()
  {
  }

  public Case(Object... prototype)
  {
    for (int i = 0; i < prototype.length; i++)
    {
      this.put(i, prototype[i]);
    }
  }

  public static Case ofLength(int size)
  {
    Case c = new Case();
    for (int i = 1; i <= size; i++)
    {
      c.put(("" + i), null);
    }
    return c;
  }

  public static void resetRandom()
  {
    random = new Random(5);
  }

  public String getLastKey()
  {
    return keySet().stream().reduce((ignored, o) -> o).orElse(null);
  }

  public Case replaceNullsWithRandomParameters(Map<String, Object[]> params)
  {
    Case fixedLengthCase = ofLength(params.size()).union(this);
    for (int i = 0; i < fixedLengthCase.size(); i++)
    {
      if (fixedLengthCase.get(i) == null)
      {
        fixedLengthCase.put(i, random(params.get(convertKey(i))));
      }
    }
    return fixedLengthCase;
  }

  public boolean matches(Case pair)
  {
    final Set<String> keys = new HashSet<>(this.keySet());
    keys.retainAll(pair.keySet());
    return !keys.stream().filter(key -> nonEquals(key, this, pair)).findFirst().isPresent();
  }

  public Object put(String key, Object value)
  {
    return this.map.put(key, value);
  }

  public Object put(int key, Object value)
  {
    return this.map.put(convertKey(key), value);
  }

  public static String convertKey(int key)
  {
    return Integer.toString(key + 1);
  }

  public Collection<String> keySet()
  {
    return this.map.keySet();
  }

  public Set<Map.Entry<String, Object>> entrySet()
  {
    return this.map.entrySet();
  }

  public Object get(int i)
  {
    return this.get(String.valueOf(i + 1));
  }

  public Object get(Object key)
  {
    return this.map.get(key);
  }

  public void putAll(Map<? extends String, ?> pair)
  {
    this.map.putAll(pair);
  }

  public Case union(Case pair)
  {
    this.putAll(pair.map);
    return this;
  }

  private static boolean nonEquals(String key, Case first, Case second)
  {
    final Object f = first.get(key);
    final Object s = second.get(key);
    return Objects.nonNull(f) && Objects.nonNull(s) && !f.equals(s);
  }

  @Override
  public String toString()
  {
    return map.toString();
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Case aCase = (Case) o;
    return Objects.equals(map, aCase.map);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(map);
  }

  public int size()
  {
    return Query.select(map.keySet().toArray(new String[0]), Integer::parseInt).max();
  }
}
