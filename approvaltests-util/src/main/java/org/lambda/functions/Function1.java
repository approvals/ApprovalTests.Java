package org.lambda.functions;

import java.util.Objects;
import java.util.function.Function;

/**
  * Findable name for java.util.function.Function
  **/
public interface Function1<In, Out>
{
  public Out call(In i);

  default <FinalOut> Function1<In, FinalOut> andThen(Function<? super Out, ? extends FinalOut> after)
  {
    Objects.requireNonNull(after);
    return (In in) -> after.apply(call(in));
  }
}
