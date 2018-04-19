package com.spun.util.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.spun.util.TimedObject;

public class TimedObjectTest
{
  @Test
  public void test() throws InterruptedException
  {
    double multiplier = TestUtils.getTimerMultiplier();
    TimedObject object = new TimedObject((long) (200 * multiplier));
    object.set(Boolean.FALSE);
    assertNotNull("Still false", object.get());
    Thread.sleep(75);
    assertNotNull("Still false", object.get());
    Thread.sleep(75);
    assertNotNull("Still false", object.get());
    Thread.sleep(75);
    assertNotNull("Still false", object.get());
    Thread.sleep(75);
    assertNotNull("Still false", object.get());
    Thread.sleep(75);
    assertNotNull("Still false", object.get());
    Thread.sleep(350);
    assertNull("Cleared", object.get());
  }
}