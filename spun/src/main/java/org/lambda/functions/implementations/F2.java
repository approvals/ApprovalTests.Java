package org.lambda.functions.implementations;

import com.spun.util.DeprecatedException;

/**
 * @deprecated use lambdas:  (a,b) -> ...your code...
 */
@Deprecated
public class F2<In1, In2, Out>
    extends
      Function<In1, In2, Object, Object, Object, Object, Object, Object, Object, Out>
    implements
      org.lambda.functions.Function2<In1, In2, Out>
{
  public F2(In1 a, In2 b, Object... extraVariables)
  {
    super(extraVariables);
    throw new DeprecatedException("(a,b) -> {/*your code*/}");
  }
  public Out call(In1 a, In2 b)
  {
    throw new DeprecatedException("(a,b) -> {/*your code*/}");
  }
}
