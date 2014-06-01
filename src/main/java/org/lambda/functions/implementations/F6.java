package org.lambda.functions.implementations;

public class F6<In1, In2, In3, In4, In5, In6, Out>
    extends
      Function<In1, In2, In3, In4, In5, In6, Object, Object, Object, Out>
    implements
      org.lambda.functions.Function6<In1, In2, In3, In4, In5, In6, Out>
{
  public F6(In1 a, In2 b, In3 c, In4 d, In5 e, In6 f, Object... extraVariables)
  {
    super(extraVariables);
    this.a = a;
    this.b = b;
    this.c = c;
    this.d = d;
    this.e = e;
    this.f = f;
  }
  public Out call(In1 a, In2 b, In3 c, In4 d, In5 e, In6 f)
  {
    return call(new Object[]{a, b, c, d, e, f});
  }
}
