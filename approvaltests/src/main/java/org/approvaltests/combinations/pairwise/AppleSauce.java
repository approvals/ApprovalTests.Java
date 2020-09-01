package org.approvaltests.combinations.pairwise;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class AppleSauce implements Case1
{
  private LinkedHashMap map = new LinkedHashMap();
  public AppleSauce()
  {
  }
  public AppleSauce(Map<String, Object> prototype)
  {
    this.putAll(prototype);
  }
  public static AppleSauce ofLength(int size)
  {
    AppleSauce c = new AppleSauce();
    for (int i = 1; i <= size; i++)
    {
      c.put(("" + i), null);
    }
    return c;
  }
  public boolean matches(AppleSauce pair)
  {
    final Set<String> keys = new HashSet<>(this.keySet());
    keys.retainAll(pair.keySet());
    return !keys.stream().filter(key -> nonEquals(key, this, pair)).findFirst().isPresent();
  }
  @Override
  public Object put(String key, Object value)
  {
    return this.map.put(key, value);
  }
  @Override
  public Collection<String> keySet()
  {
    return this.map.keySet();
  }
  @Override
  public Set<Map.Entry<String, Object>> entrySet()
  {
    return this.map.entrySet();
  }
  public Object get(int i)
  {
    return this.get(String.valueOf(i + 1));
  }
  @Override
  public Object get(Object key)
  {
    return this.map.get(key);
  }
  @Override
  public void putAll(Map<? extends String, ?> pair)
  {
    this.map.putAll(pair);
  }
  public AppleSauce union(AppleSauce pair)
  {
    this.putAll(pair.map);
    return this;
  }
  @Override
  public AppleSauce clone()
  {
    return new AppleSauce(this.map);
  }
  @Override
  public boolean matches(Case1 pair)
  {
    return matches((AppleSauce) pair);
  }
  @Override
  public Case1 union(Case1 pair)
  {
    return union((AppleSauce) pair);
  }
  private static boolean nonEquals(String key, AppleSauce first, AppleSauce second)
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
}
