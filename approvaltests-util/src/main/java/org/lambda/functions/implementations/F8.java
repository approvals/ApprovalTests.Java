package org.lambda.functions.implementations;

import com.spun.util.DeprecatedException;

/**
 * @deprecated use lambdas:  (a,b,c,d,e,f,g,h) -> ...your code...
 */
@Deprecated
public class F8<In1, In2, In3, In4, In5, In6, In7, In8, Out>
    extends
      Function<In1, In2, In3, In4, In5, In6, In7, In8, Object, Out>
    implements
      org.lambda.functions.Function8<In1, In2, In3, In4, In5, In6, In7, In8, Out>
{
  public F8(In1 a, In2 b, In3 c, In4 d, In5 e, In6 f, In7 g, In8 h, Object... extraVariables)
  {
    super(extraVariables);
    throw new DeprecatedException("(a,b,c,d,e,f,g,h) -> {/*your code*/}");
  }
  public Out call(In1 a, In2 b, In3 c, In4 d, In5 e, In6 f, In7 g, In8 h)
  {
    throw new DeprecatedException("(a,b,c,d,e,f,g,h) -> {/*your code*/}");
  }
}
