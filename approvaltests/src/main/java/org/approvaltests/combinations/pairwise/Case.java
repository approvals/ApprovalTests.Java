package org.approvaltests.combinations.pairwise;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Case extends LinkedHashMap<String, Object> implements Cloneable
{
  public Case()
  {
    super();
  }
  public Case(Map<String, Object> prototype)
  {
    this.putAll(prototype);
  }
  public static Case ofLength(int size)
  {
    Case c = new Case();
    for (int i = 1; i <= size; i++)
    {
      c.put(("p" + i), null);
    }
    return c;
  }
  public boolean matches(Case pair)
  {
    final Set<String> keys = new HashSet<>(this.keySet());
    keys.retainAll(pair.keySet());
    return !keys.stream().filter(key -> nonEquals(key, this, pair)).findFirst().isPresent();
  }
  public Object get(int i)
  {
    return this.get("p" + (i + 1));
  }
  public Case union(Case pair)
  {
    this.putAll(pair);
    return this;
  }
  @Override
  public Case clone()
  {
    return new Case(this);
  }
  private static boolean nonEquals(String key, Case first, Case second)
  {
    final Object f = first.get(key);
    final Object s = second.get(key);
    return Objects.nonNull(f) && Objects.nonNull(s) && !f.equals(s);
  }
}
