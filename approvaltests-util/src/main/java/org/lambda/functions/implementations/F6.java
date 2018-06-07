package org.lambda.functions.implementations;

import com.spun.util.DeprecatedException;

/**
 * @deprecated use lambdas:  (a,b,c,d,e,f) {@literal -> ...your code...}
 */
@Deprecated
public class F6<In1, In2, In3, In4, In5, In6, Out>
    extends
      Function<In1, In2, In3, In4, In5, In6, Object, Object, Object, Out>
    implements
      org.lambda.functions.Function6<In1, In2, In3, In4, In5, In6, Out>
{
  public F6(In1 a, In2 b, In3 c, In4 d, In5 e, In6 f, Object... extraVariables)
  {
    super(extraVariables);
    throw new DeprecatedException("(a,b,c,d,e,f) -> {/*your code*/}");
  }
  public Out call(In1 a, In2 b, In3 c, In4 d, In5 e, In6 f)
  {
    throw new DeprecatedException("(a,b,c,d,e,f) -> {/*your code*/}");
  }
}
