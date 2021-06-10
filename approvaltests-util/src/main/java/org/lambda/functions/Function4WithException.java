package org.lambda.functions;

public interface Function4WithException<In1, In2, In3, In4, Out>
{
  public Out call(In1 a, In2 b, In3 c, In4 d) throws Throwable;
}
