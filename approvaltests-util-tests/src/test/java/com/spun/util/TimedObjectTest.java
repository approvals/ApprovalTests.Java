package com.spun.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.lambda.utils.Mutable;

public class TimedObjectTest
{
  @Test
  public void test() throws InterruptedException
  {
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
    ThreadUtils.sleep(300);
    assertNull(object.get(), "Cleared");
  }
}