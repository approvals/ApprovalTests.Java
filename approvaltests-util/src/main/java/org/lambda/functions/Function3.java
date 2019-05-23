package org.lambda.functions;

public interface Function3<In1, In2, In3, Out>
{
  // startcode function3_call
  public Out call(In1 a, In2 b, In3 c);
  // endcode
}