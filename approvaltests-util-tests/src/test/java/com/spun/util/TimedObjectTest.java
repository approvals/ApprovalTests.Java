package com.spun.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.spun.util.tests.TestUtils;

public class TimedObjectTest
{
  @Test
  public void test() throws InterruptedException
  {
    double multiplier = TestUtils.getTimerMultiplier();
    TimedObject object = new TimedObject((long) (200 * multiplier));
    object.set(Boolean.FALSE);
    assertNotNull(object.get(), "Still false");
    Thread.sleep(75);
    assertNotNull(object.get(), "Still false");
    Thread.sleep(75);
    assertNotNull(object.get(), "Still false");
    Thread.sleep(75);
    assertNotNull(object.get(), "Still false");
    Thread.sleep(75);
    assertNotNull(object.get(), "Still false");
    Thread.sleep(75);
    assertNotNull(object.get(), "Still false");
    Thread.sleep(350);
    assertNull(object.get(), "Cleared");
  }
}