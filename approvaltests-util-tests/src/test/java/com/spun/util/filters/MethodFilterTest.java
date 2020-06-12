package com.spun.util.filters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.lambda.query.Query;

public class MethodFilterTest
{
  @Test
  public void testDate() throws Exception
  {
    SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
    Date dates[] = {dateParser.parse("2006-01-01"), dateParser.parse("2006-12-01")};
    Date date = dateParser.parse("2006-06-01");
    List<Date> results = Query.where(dates, d -> date.getTime() >= d.getTime());
    assertEquals(dates[0], results.get(0));
    assertEquals(1, results.size());
  }
}
