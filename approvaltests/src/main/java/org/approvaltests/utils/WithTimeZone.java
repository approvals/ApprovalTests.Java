package org.approvaltests.utils;

import java.util.TimeZone;

public class WithTimeZone implements AutoCloseable {

  private final TimeZone tz;

  public WithTimeZone() {
      this("UTC");
  }

  public WithTimeZone(String zoneId) {
      tz = TimeZone.getDefault();
      TimeZone.setDefault(TimeZone.getTimeZone(zoneId));
  }

  @Override
  public void close() {
      TimeZone.setDefault(tz);
  }
}
