package com.spun.util.parser.tests;

import com.spun.util.DateUtils;
import org.approvaltests.velocity.VelocityApprovals;
import org.junit.Test;

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
    VelocityApprovals.verify(c -> c.put("array", Arrays.asList("one", "two", "three")), ".html");
  }
  @Test
  public void testDate()
  {
      Timestamp date = DateUtils.parse("2001/02/03");
      // if you are in CET then set the timezone to EST it changes the date to the day before!
      //TemplateDate templateDate = ParserCommons.asDate(date);
      //System.out.println(templateDate.getDate("SHORT", "EST"));
      //System.out.println(templateDate.getTime("SHORT", "EST"));
      VelocityApprovals.verify(c -> {
        c.put("date", date);
    });
  }
}
