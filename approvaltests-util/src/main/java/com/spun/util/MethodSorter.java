package com.spun.util;

/**
 * @deprecated use {@code OrderBy.descending(a -> a.length()) }
 */
@Deprecated
public class MethodSorter<T>
{
  public MethodSorter(Class<? extends T> classType, String methodName, boolean ascending)
  {
    throw new DeprecatedException("OrderBy.%s((%s a) -> a.%s())", ascending ? "ascending" : "descending",
        classType.getName(), methodName);
  }
}
