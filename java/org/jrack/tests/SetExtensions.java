package org.jrack.tests;

import java.util.Arrays;
import java.util.Set;

public class SetExtensions
{
  public static Object[] sort(Set<? extends Object> set)
  {
    Object[] keySet = set.toArray();
    Arrays.sort(keySet);
    return keySet;
  }
}