/**
 * 
 */
package org.jrack.tests;

import java.util.Arrays;
import java.util.Set;

public class SetExtensions extends ExtendableBase<Set>
{
  public Object[] sorted()
  {
    return sort(getCaller());
  }

  public static Object[] sort(Set set)
  {
    Object[] keySet = set.toArray();
    Arrays.sort(keySet);
    return keySet;
  }
}