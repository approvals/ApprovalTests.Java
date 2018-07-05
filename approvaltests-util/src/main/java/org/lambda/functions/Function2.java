package org.lambda.functions;

/**
 * Findable name for java.util.function.BiFunction
 **/
public interface Function2<In1, In2, Out>
{
  public Out call(In1 a, In2 b);
}
