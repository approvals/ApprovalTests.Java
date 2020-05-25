package com.spun.util.parser.tests;

import com.spun.util.DateUtils;
import org.approvaltests.velocity.VelocityApprovals;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.TimeZone;

//@UseReporter(FileLauncherReporter.class)
public class VelocityUtilsTest
{
  @Test
  public void testArray()
  {
    VelocityApprovals.verify(c -> c.put("array", new String[]{"one", "two", "three", "four", "five"}));
  }
  @Test
  public void testList()
  {
    VelocityApprovals.verify(c -> c.put("array", Arrays.asList("one", "two", "three")), ".html");
  }
  @Test
  public void testDate()
  {
    try (ConsistentTimeZone tz = new ConsistentTimeZone()) {
      Timestamp date = DateUtils.parse("2001/02/03");
      VelocityApprovals.verify(c -> {
        c.put("date", date);
      });
    }
  }

  static class ConsistentTimeZone implements AutoCloseable {

    private final TimeZone tz;

    public ConsistentTimeZone() {
        tz = TimeZone.getDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public void close() {
        TimeZone.setDefault(tz);
    }
  }
}
