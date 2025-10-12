package com.spun.util.filters;

import com.spun.util.DeprecatedException;

/**
 * @deprecated use lambdas: {@literal a -> filter1 && filter2}
 */
@Deprecated
public class AndFilter<T> implements Filter<T>
{
  public AndFilter(Filter<T> filter1, Filter<T> filter2)
  {
  }

  public boolean isExtracted(Object object)
  {
    throw new DeprecatedException(" a -> filter1 && filter2");
  }
}