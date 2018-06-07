package org.lambda.functions.implementations;

import com.spun.util.DeprecatedException;

/**
 * @deprecated use lambdas:  {@literal -> ...your code...}
 */
@Deprecated
public class F1<In, Out> extends Function<In, Object, Object, Object, Object, Object, Object, Object, Object, Out>
    implements
      org.lambda.functions.Function1<In, Out>
{
  public F1(In a, Object... extraVariables)
  {
    super(null);
    throw new DeprecatedException("a -> {/*your code*/}");
  }
  public Out call(In i)
  {
    throw new DeprecatedException("a -> {/*your code*/}");
  }
}
