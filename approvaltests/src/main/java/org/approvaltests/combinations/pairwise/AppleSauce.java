package org.approvaltests.combinations.pairwise;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class AppleSauce extends LinkedHashMap<String, Object> implements Cloneable, Case1
{
  public AppleSauce()
  {
    super();
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
  public Object get(int i)
  {
    return this.get(String.valueOf(i + 1));
  }
  public AppleSauce union(AppleSauce pair)
  {
    this.putAll(pair);
    return this;
  }
  @Override
  public AppleSauce clone()
  {
    return new AppleSauce(this);
  }
  private static boolean nonEquals(String key, AppleSauce first, AppleSauce second)
  {
    final Object f = first.get(key);
    final Object s = second.get(key);
    return Objects.nonNull(f) && Objects.nonNull(s) && !f.equals(s);
  }
}
