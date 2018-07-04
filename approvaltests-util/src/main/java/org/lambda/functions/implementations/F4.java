package org.lambda.functions.implementations;

import com.spun.util.DeprecatedException;

/**
 * @deprecated use lambdas:  (a,b,c,d) {@literal -> ...your code...}
 */
@Deprecated
public class F4<In1, In2, In3, In4, Out>
    extends
      Function<In1, In2, In3, In4, Object, Object, Object, Object, Object, Out>
    implements
      org.lambda.functions.Function4<In1, In2, In3, In4, Out>
{
  public F4(In1 a, In2 b, In3 c, In4 d, Object... extraVariables)
  {
    super(extraVariables);
    throw new DeprecatedException("(a,b,c,d) -> {/*your code*/}");
  }
  public Out call(In1 a, In2 b, In3 c, In4 d)
  {
    throw new DeprecatedException("(a,b,c,d) -> {/*your code*/}");
  }
}
