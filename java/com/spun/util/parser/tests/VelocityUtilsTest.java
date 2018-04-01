package com.spun.util.parser.tests;

import java.util.Arrays;

import org.approvaltests.velocity.VelocityApprovals;
import org.junit.Test;

import com.spun.util.DateUtils;

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
    VelocityApprovals.verify(c -> c.put("date", DateUtils.parse("2001/02/03")));
  }
}
