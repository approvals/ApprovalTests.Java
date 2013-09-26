package org.lambda.functions.implementations;

public class F3<In1, In2, In3, Out>
    extends
      Function<In1, In2, In3, Object, Object, Object, Object, Object, Object, Out>
    implements
      org.lambda.functions.Function3<In1, In2, In3, Out>
{
  public F3(In1 a, In2 b, In3 c, Object... extraVariables)
  {
    super(extraVariables);
    this.a = a;
    this.b = b;
    this.c = c;
  }
  public Out call(In1 a, In2 b, In3 c)
  {
    return call(new Object[]{a, b, c,});
  }
}
