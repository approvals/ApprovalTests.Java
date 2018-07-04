package org.lambda.functions.implementations;

import com.spun.util.DeprecatedException;

/**
 * @deprecated use lambdas:  (a,b,c,d,e,f,g,h,i) {@literal -> ...your code...}
 */
@Deprecated
public class F9<In1, In2, In3, In4, In5, In6, In7, In8, In9, Out>
    extends
      Function<In1, In2, In3, In4, In5, In6, In7, In8, In9, Out>
    implements
      org.lambda.functions.Function9<In1, In2, In3, In4, In5, In6, In7, In8, In9, Out>
{
  public F9(In1 a, In2 b, In3 c, In4 d, In5 e, In6 f, In7 g, In8 h, In9 i, Object... extraVariables)
  {
    super(extraVariables);
    throw new DeprecatedException("(a,b,c,d,e,f,g,h,i)  -> {/*your code*/}");
  }
  public Out call(In1 a, In2 b, In3 c, In4 d, In5 e, In6 f, In7 g, In8 h, In9 i)
  {
    throw new DeprecatedException("(a,b,c,d,e,f,g,h,i)  -> {/*your code*/}");
  }
}
