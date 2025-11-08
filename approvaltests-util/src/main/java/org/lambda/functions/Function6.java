package org.lambda.functions;

import java.util.Objects;
import java.util.function.Function;

public interface Function6<In1, In2, In3, In4, In5, In6, Out>
{
  public Out call(In1 a, In2 b, In3 c, In4 d, In5 e, In6 f);

  default <FinalOut> Function6<In1, In2, In3, In4, In5, In6, FinalOut> andThen(
      Function<? super Out, ? extends FinalOut> after)
  {
    Objects.requireNonNull(after);
    return (In1 a, In2 b, In3 c, In4 d, In5 e, In6 f) -> after.apply(call(a, b, c, d, e, f));
  }
}
