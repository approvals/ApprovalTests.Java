package org.lambda.functions;

import java.util.Objects;
import java.util.function.Function;

public interface Function3<In1, In2, In3, Out>
{
  // begin-snippet: function3_call
  public Out call(In1 a, In2 b, In3 c);
  // end-snippet

  default <FinalOut> Function3<In1, In2, In3, FinalOut> andThen(Function<? super Out, ? extends FinalOut> after)
  {
    Objects.requireNonNull(after);
    return (In1 a, In2 b, In3 c) -> after.apply(call(a, b, c));
  }
}
