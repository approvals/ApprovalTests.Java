package org.lambda.functions.implementations;

public class F2<In1, In2, Out>
    extends
      Function<In1, In2, Object, Object, Object, Object, Object, Object, Object, Out>
    implements
      org.lambda.functions.Function2<In1, In2, Out>
{
  public F2(In1 a, In2 b, Object... extraVariables)
  {
    super(extraVariables);
    this.a = a;
    this.b = b;
  }
  public Out call(In1 a, In2 b)
  {
    return call(new Object[]{a, b});
  }
}
