package org.lambda.functions;

import java.util.Objects;
import java.util.function.Function;

/**
  * Findable name for java.util.function.Supplier
  **/
public interface Function0<Out>
{
  public Out call();

  default <FinalOut> Function0<FinalOut> andThen(Function<? super Out, ? extends FinalOut> after)
  {
    Objects.requireNonNull(after);
    return () -> after.apply(call());
  }
}
