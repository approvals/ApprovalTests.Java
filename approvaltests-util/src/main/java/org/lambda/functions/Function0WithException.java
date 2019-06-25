package org.lambda.functions;

/**
  * Findable name for java.util.function.Supplier that allows exceptions
  **/
public interface Function0WithException<Out>
{
  public Out call() throws Throwable;
}
