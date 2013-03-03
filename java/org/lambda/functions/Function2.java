package org.lambda.functions;

public interface Function2<In1, In2, Out>
{
  public Out call(In1 a, In2 b);
}
