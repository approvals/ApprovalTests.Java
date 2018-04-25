package org.lambda.functions.implementations;

import com.spun.util.DeprecatedException;

/**
 * @deprecated use lambdas:  (a,b,c) -> ...your code...
 */
@Deprecated
public class F3<In1, In2, In3, Out>
    extends
      Function<In1, In2, In3, Object, Object, Object, Object, Object, Object, Out>
    implements
      org.lambda.functions.Function3<In1, In2, In3, Out>
{
  public F3(In1 a, In2 b, In3 c, Object... extraVariables)
  {
    super(extraVariables);
    throw new DeprecatedException("(a,b,c) -> {/*your code*/}");
  }
  public Out call(In1 a, In2 b, In3 c)
  {
    throw new DeprecatedException("(a,b,c) -> {/*your code*/}");
  }
}
