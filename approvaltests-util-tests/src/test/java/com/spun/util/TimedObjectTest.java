package com.spun.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.spun.util.logger.SimpleLogger;
import org.junit.jupiter.api.Test;

import com.spun.util.tests.TestUtils;

public class TimedObjectTest
{
  @Test
  public void test() throws InterruptedException
  {
    SimpleLogger.message(String.format("Be aware that this test %s  can randomly fail if the thread is interrupted during execution", this.getClass().getSimpleName()));
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