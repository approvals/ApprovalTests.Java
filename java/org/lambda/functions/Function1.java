package org.lambda.functions;

public interface Function1<In, Out>
{
  public Out call(In i);
}
