package org.lambda.query;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.lambda.actions.implementations.A0;
import org.lambda.functions.Function1;

import com.spun.util.ThreadLauncher;

public class ParallelQuery
{
  public static <In> List<In> where(Iterable<In> list, final Function1<In, Boolean> funct)
  {
    final ArrayList<In> out = new ArrayList<In>();
    final AtomicInteger done = new AtomicInteger();
    int count = 0;
    for (In i : list)
    {
      count++;
      final In piece = i;
      ThreadLauncher.launch(new A0(false, funct, piece, out, done)
      {
        {
          if (run)
          {
            if (funct.call(piece))
            {
              out.add(piece);
            }
            done.incrementAndGet();
          }
        }
      });
    }
    while (done.get() != count)
    {
      try
      {
        Thread.sleep(2);
      }
      catch (InterruptedException e)
      {
        // do nothing
      }
    }
    return out;
  }
}
