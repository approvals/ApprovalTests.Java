package org.lambda.functions.implementations;

public class F5<In1, In2, In3, In4, In5, Out>
    extends
      Function<In1, In2, In3, In4, In5, Object, Object, Object, Object, Out>
    implements
      org.lambda.functions.Function5<In1, In2, In3, In4, In5, Out>
{
  public F5(In1 a, In2 b, In3 c, In4 d, In5 e, Object... extraVariables)
  {
    super(extraVariables);
    this.a = a;
    this.b = b;
    this.c = c;
    this.d = d;
    this.e = e;
  }
  public Out call(In1 a, In2 b, In3 c, In4 d, In5 e)
  {
    return call(new Object[]{a, b, c, d, e});
  }
}
