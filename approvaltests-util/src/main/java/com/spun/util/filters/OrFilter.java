package com.spun.util.filters;

import com.spun.util.DeprecatedException;

/**
 * Listens to the state of a EnabledConditions object
 * @deprecated use lambdas:  {@literal a -> filter1 || filter2 }
 */
@Deprecated
public class OrFilter<T> implements Filter<T>
{
  public OrFilter(Filter<T> filter1, Filter<T> filter2)
  {
    throw new DeprecatedException("a -> filter1 || filter2");
  }

  public boolean isExtracted(Object object)
  {
    throw new DeprecatedException("a -> filter1 || filter2");
  }
}