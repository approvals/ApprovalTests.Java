package org.lambda.functions;

import java.util.Objects;
import java.util.function.Function;

public interface Function4<In1, In2, In3, In4, Out>
{
  public Out call(In1 a, In2 b, In3 c, In4 d);

  default <FinalOut> Function4<In1, In2, In3, In4, FinalOut> andThen(
      Function<? super Out, ? extends FinalOut> after)
  {
    Objects.requireNonNull(after);
    return (In1 a, In2 b, In3 c, In4 d) -> after.apply(call(a, b, c, d));
  }
}
