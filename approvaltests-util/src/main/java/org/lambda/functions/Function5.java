package org.lambda.functions;

import java.util.Objects;
import java.util.function.Function;

public interface Function5<In1, In2, In3, In4, In5, Out>
{
  public Out call(In1 a, In2 b, In3 c, In4 d, In5 e);

  default <FinalOut> Function5<In1, In2, In3, In4, In5, FinalOut> andThen(
      Function<? super Out, ? extends FinalOut> after)
  {
    Objects.requireNonNull(after);
    return (In1 a, In2 b, In3 c, In4 d, In5 e) -> after.apply(call(a, b, c, d, e));
  }
}
