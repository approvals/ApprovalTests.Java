package com.spun.util.parser.tests;

import java.util.Arrays;

import org.apache.velocity.context.Context;
import org.approvaltests.velocity.VelocityApprovals;
import org.junit.Test;

import com.spun.util.DateUtils;
import com.spun.util.velocity.ContextAware;

//@UseReporter(FileLauncherReporter.class)
public class VelocityUtilsTest implements ContextAware
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
  public void setupContext(Context context)
  {
    context.put("name", "llewellyn");
    context.put("date", DateUtils.parse("2001/02/03"));
    context.put("array", new String[]{"one", "two", "three", "four", "five"});
    context.put("list", Arrays.asList("one", "two", "three", "four", "five"));
  }
}
