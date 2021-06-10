package org.lambda.functions;

public interface Function3WithException<In1, In2, In3, Out>
{
  // begin-snippet: function3_call
  public Out call(In1 a, In2 b, In3 c) throws Throwable;
  // end-snippet
}