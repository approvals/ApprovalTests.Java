package org.lambda.functions.implementations;

public class F4<In1, In2, In3, In4, Out>
    extends
      Function<In1, In2, In3, In4, Object, Object, Object, Object, Object, Out>
    implements
      org.lambda.functions.Function4<In1, In2, In3, In4, Out>
{
  public F4(In1 a, In2 b, In3 c, In4 d, Object... extraVariables)
  {
    super(extraVariables);
    this.a = a;
    this.b = b;
    this.c = c;
    this.d = d;
  }
  public Out call(In1 a, In2 b, In3 c, In4 d)
  {
    return call(new Object[]{a, b, c, d});
  }
}
