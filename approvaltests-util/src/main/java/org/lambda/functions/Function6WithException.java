package org.lambda.functions;

public interface Function6WithException<In1, In2, In3, In4, In5, In6, Out>
{
  public Out call(In1 a, In2 b, In3 c, In4 d, In5 e, In6 f) throws Throwable;
}
