package org.lambda.query;

import org.lambda.actions.Action1;

public class Loops
{
  public static <In> void forEach(Iterable<In> list, Action1<In> doForEach)
  {
    for (In in : list)
    {
      doForEach.call(in);
    }
  }
}
