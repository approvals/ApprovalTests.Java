package org.approvaltests.utils;

import java.util.TimeZone;

public class WithTimeZone implements AutoCloseable {

  private final TimeZone tz;

  public WithTimeZone() {
      tz = TimeZone.getDefault();
      TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }

  @Override
  public void close() {
      TimeZone.setDefault(tz);
  }
}
