package com.spun.util.parser;

import com.spun.util.DateUtils;
import org.approvaltests.core.Options;
import org.approvaltests.utils.WithTimeZone;
import org.approvaltests.velocity.VelocityApprovals;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Arrays;

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
    VelocityApprovals.verify(c -> c.put("array", Arrays.asList("one", "two", "three")),
        new Options().forFile().withExtension(".html"));
  }
  @Test
  public void testDate()
  {
    try (WithTimeZone tz = new WithTimeZone())
    {
      Timestamp date = DateUtils.parse("2001/02/03");
      VelocityApprovals.verify(c -> c.put("date", date), new Options().forFile().withExtension(".md"));
    }
  }
  @Test
  public void testWithTimeZoneExample()
  {
    // begin-snippet: with_time_zone
    try (WithTimeZone tz = new WithTimeZone("UTC"))
    {
      // All code within this block will see the computer as being in the UTC time zone
    }
    // The computer's time zone will revert to previous setting here
    // end-snippet
  }
}
