package org.lambda.functions.implementations;

import com.spun.util.DeprecatedException;

/**
 * @deprecated use lambdas:  (a,b,c,d,e,f,g,h,i) {@literal -> ...your code...}
 */
@Deprecated
public class F0<Out> extends Function<Object, Object, Object, Object, Object, Object, Object, Object, Object, Out>
    implements
      org.lambda.functions.Function0<Out>
{
  public F0(Object... extraVariables)
  {
    super(extraVariables);
    throw new DeprecatedException("() -> {/*your code*/}");
  }
  public Out call()
  {
    throw new DeprecatedException("() -> {/*your code*/}");
  }
}
