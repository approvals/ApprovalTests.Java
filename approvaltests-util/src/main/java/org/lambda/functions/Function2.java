package org.lambda.functions;

import java.util.Objects;
import java.util.function.Function;

/**
 * Findable name for java.util.function.BiFunction
 **/
public interface Function2<In1, In2, Out>
{
  public Out call(In1 a, In2 b);

  default <FinalOut> Function2<In1, In2, FinalOut> andThen(Function<? super Out, ? extends FinalOut> after)
  {
    Objects.requireNonNull(after);
    return (In1 a, In2 b) -> after.apply(call(a, b));
  }
}
