package org.lambda.functions.implementations;

import com.spun.util.DeprecatedException;

/**
 * @deprecated use lambdas:  (a,b,c,d,e) -> ...your code...
 */
@Deprecated
public class F5<In1, In2, In3, In4, In5, Out>
    extends
      Function<In1, In2, In3, In4, In5, Object, Object, Object, Object, Out>
    implements
      org.lambda.functions.Function5<In1, In2, In3, In4, In5, Out>
{
  public F5(In1 a, In2 b, In3 c, In4 d, In5 e, Object... extraVariables)
  {
    super(extraVariables);
    throw new DeprecatedException("(a,b,c,d,e) -> {/*your code*/}");
  }
  public Out call(In1 a, In2 b, In3 c, In4 d, In5 e)
  {
    throw new DeprecatedException("(a,b,c,d,e) -> {/*your code*/}");
  }
}
