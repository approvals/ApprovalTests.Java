package com.spun.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.RepeatedTest;
import org.lambda.utils.Mutable;

public class TimedObjectTest
{
  @RepeatedTest(10000)
  public void test() throws InterruptedException
  {
//    Long[] time = new Long[]{0L};
    Mutable<Long> time = new Mutable<>(0L);
    TimedObject object = new TimedObject(100L, time::get);
    object.set(Boolean.FALSE);
    assertNotNull(object.get(), "Still false");
    time.update(l -> l + 75);
    assertNotNull(object.get(), "Still false");
    time.update(l -> l + 75);
    assertNotNull(object.get(), "Still false");
    time.update(l -> l + 75);
    assertNotNull(object.get(), "Still false");
    time.update(l -> l + 75);
    assertNotNull(object.get(), "Still false");
    time.update(l -> l + 75);
    assertNotNull(object.get(), "Still false");
    time.update(l -> l + 350);
    ThreadUtils.sleep(150);
    assertNull(object.get(), "Cleared");
  }
}