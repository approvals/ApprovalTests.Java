package org.lambda.query;

import java.util.concurrent.atomic.AtomicInteger;

import org.lambda.actions.Action1;
import org.lambda.actions.implementations.A0;

import com.spun.util.ThreadLauncher;

public class ParallelLoops
{
  public static <T> void forEach(Iterable<T> list, final Action1<T> a1)
  {
    final AtomicInteger done = new AtomicInteger();
    int count = 0;
    for (T i : list)
    {
      count++;
      final T piece = i;
      ThreadLauncher.launch(new A0(false, a1, piece, done)
      {
        {
          if (run)
          {
            a1.call(piece);
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
  }
}
