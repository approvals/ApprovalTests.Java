package org.lambda.query;

import org.lambda.functions.Function1;

/**
 * A super fast Lambda that simply return whatever is sent
 **/
public class Echo1<T> implements Function1<T, T>
{
  public T call(T i)
  {
    return i;
  }
}
