package org.lambda.functions.implementations;

public class F9<In1, In2, In3, In4, In5, In6, In7, In8, In9, Out>
    extends
      Function<In1, In2, In3, In4, In5, In6, In7, In8, In9, Out>
    implements
      org.lambda.functions.Function9<In1, In2, In3, In4, In5, In6, In7, In8, In9, Out>
{
  public F9(In1 a, In2 b, In3 c, In4 d, In5 e, In6 f, In7 g, In8 h, In9 i, Object... extraVariables)
  {
    super(extraVariables);
    this.a = a;
    this.b = b;
    this.c = c;
    this.d = d;
    this.e = e;
    this.f = f;
    this.g = g;
    this.h = h;
    this.i = i;
  }
  public Out call(In1 a, In2 b, In3 c, In4 d, In5 e, In6 f, In7 g, In8 h, In9 i)
  {
    return call(new Object[]{a, b, c, d, e, f, g, h, i});
  }
}
