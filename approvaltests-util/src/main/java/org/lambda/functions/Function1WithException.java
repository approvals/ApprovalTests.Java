package org.lambda.functions;

/**
  * Findable name for java.util.function.Function
  **/
public interface Function1WithException<In, Out>
{
  public Out call(In i) throws Throwable;
}
