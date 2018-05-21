package org.lambda.functions;

/**
  * Findable name for java.util.function.Function
  **/
public interface Function1<In, Out>
{
  public Out call(In i);
}
