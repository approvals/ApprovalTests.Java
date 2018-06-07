package org.lambda.query;

import org.lambda.functions.Function1;

import com.spun.util.DeprecatedException;

/**
 * @deprecated use lambdas: {@literal  a -> a }
 */
@Deprecated
public class Echo1<T> implements Function1<T, T>
{
  public T call(T i)
  {
    throw new DeprecatedException("a -> a");
  }
}
