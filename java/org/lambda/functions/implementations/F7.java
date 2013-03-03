package org.lambda.functions.implementations;

public class F7<In1, In2, In3, In4, In5, In6, In7, Out>
    extends
      Function<In1, In2, In3, In4, In5, In6, In7, Object, Object, Out>
    implements
      org.lambda.functions.Function7<In1, In2, In3, In4, In5, In6, In7, Out>
{
  public F7(In1 a, In2 b, In3 c, In4 d, In5 e, In6 f, In7 g, Object... extraVariables)
  {
    super(extraVariables);
    this.a = a;
    this.b = b;
    this.c = c;
    this.d = d;
    this.e = e;
    this.f = f;
    this.g = g;
  }
  public Out call(In1 a, In2 b, In3 c, In4 d, In5 e, In6 f, In7 g)
  {
    return call(new Object[]{a, b, c, d, e, f, g});
  }
}
