package org.approvaltests.utils;

import java.util.TimeZone;
import java.util.concurrent.locks.ReentrantLock;

public class WithTimeZone implements AutoCloseable
{
  private static final ReentrantLock defaultTimeZoneLock = new ReentrantLock();
  private final TimeZone             tz;
  public WithTimeZone()
  {
    this("UTC");
  }

  public WithTimeZone(String zoneId)
  {
    tz = TimeZone.getDefault();
    defaultTimeZoneLock.lock();
    TimeZone.setDefault(TimeZone.getTimeZone(zoneId));
  }

  @Override
  public void close()
  {
    TimeZone.setDefault(tz);
    defaultTimeZoneLock.unlock();
  }
}
