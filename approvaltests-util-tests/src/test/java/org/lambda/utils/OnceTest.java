package org.lambda.utils;

import com.spun.util.NumberUtils;
import com.spun.util.ThreadUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OnceTest
{
  public static int[] globals = {0, 0, 0};
  @Test
  public void testOnce()
  {
    increment();
    increment();
    increment();
    increment();
    increment();
    assertEquals(1, globals[0]);
  }
  private void increment()
  {
    var fieldThatForcesLambdaToHaveMultipleInstances = NumberUtils.doRandomPercentage(50);
    Once.run(() -> {
      if (fieldThatForcesLambdaToHaveMultipleInstances)
        globals[0]++;
      else
        globals[0]++;
    });
  }
  @Test
  void testOnceNormalLambda()
  {
    incrementNormal();
    incrementNormal();
    incrementNormal();
    incrementNormal();
    incrementNormal();
    assertEquals(1, globals[1]);
  }
  private void incrementNormal()
  {
    Once.run(() -> globals[1]++);
  }
  @Test
  @Timeout(unit = TimeUnit.SECONDS, value = 1)
  void testOnceAsync()
  {
    incrementAsync();
    incrementAsync();
    incrementAsync();
    incrementAsync();
    incrementAsync();
    ThreadUtils.sleep(750);
    assertEquals(1, globals[2]);
  }
  private void incrementAsync()
  {
    Once.runAsync(() -> {
      ThreadUtils.sleep(500);
      globals[2]++;
    });
  }
  @Test
  public void testFunctionCallOnce()
  {
    int count = 0;
    count = increment(count);
    count = increment(count);
    count = increment(count);
    count = increment(count);
    count = increment(count);
    assertEquals(1, count);
  }
  private int increment(int count)
  {
    return Once.run(() -> count + 1);
  }
}
