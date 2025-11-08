package org.lambda.functions;

import java.util.Objects;
import java.util.function.Function;

public interface Function9<In1, In2, In3, In4, In5, In6, In7, In8, In9, Out>
{
  public Out call(In1 a, In2 b, In3 c, In4 d, In5 e, In6 f, In7 g, In8 h, In9 i);

  default <FinalOut> Function9<In1, In2, In3, In4, In5, In6, In7, In8, In9, FinalOut> andThen(
      Function<? super Out, ? extends FinalOut> after)
  {
    Objects.requireNonNull(after);
    return (In1 a, In2 b, In3 c, In4 d, In5 e, In6 f, In7 g, In8 h, In9 i) -> after
        .apply(call(a, b, c, d, e, f, g, h, i));
  }
}
