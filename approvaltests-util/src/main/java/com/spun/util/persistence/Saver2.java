package com.spun.util.persistence;

import com.spun.util.Tuple;
import org.lambda.actions.Action2;
import org.lambda.functions.Function2;

public class Saver2
{
  public static <In1, In2> Saver<Tuple<In1, In2>> create(Action2<In1, In2> lambda)
  {
    return t -> {
      lambda.call(t.getFirst(), t.getSecond());
      return t;
    };
  }
  public static <In1, In2> Saver<Tuple<In1, In2>> create(Function2<In1, In2, Tuple<In1, In2>> lambda)
  {
    return t -> lambda.call(t.getFirst(), t.getSecond());
  }
}
