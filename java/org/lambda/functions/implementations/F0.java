package org.lambda.functions.implementations;

public class F0<Out> extends Function<Object, Object, Object, Object, Object, Object, Object, Object, Object, Out>
    implements
      org.lambda.functions.Function0<Out>
{
  public F0(Object... extraVariables)
  {
    super(extraVariables);
  }
  public Out call()
  {
    return call(new Object[0]);
  }
}
