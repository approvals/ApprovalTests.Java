package org.lambda.functions.implementations;

public class F1<In, Out> extends Function<In, Object, Object, Object, Object, Object, Object, Object, Object, Out>
    implements
      org.lambda.functions.Function1<In, Out>
{
  public F1(In a, Object... extraVariables)
  {
    super(extraVariables);
    this.a = a;
  }
  public Out call(In i)
  {
    return call(new Object[]{i});
  }
}
