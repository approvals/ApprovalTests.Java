package org.lambda.query;

import com.spun.util.DateUtils;
import java.sql.Timestamp;
import java.util.List;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;
import org.lambda.query.OrderBy.Order;

public class OrderByTest
{
  @Test
  public void testInts()
  {
    Integer[] numbers = {6, 5, 8, 4, 9, 10};
    Approvals.verifyAll("i", Query.orderBy(numbers, a -> a));
  }
  @Test
  public void testString()
  {
    String[] names = {"Robert", "Lynn", "Samantha", "Marsha", "Llewellyn"};
    Approvals.verifyAll("i", Query.orderBy(names, a -> a));
  }
  @Test
  public void testStringMethods()
  {
    String[] names = {"Robert", "Lynn", "Samantha", "Marsha", "Llewellynn"};
    Approvals.verifyAll("i", Query.orderBy(names, a -> a.length()));
  }
  @Test
  public void testDates()
  {
    String[] dates = {"2010/05/21", "1975/06/28", "2000/01/01", "1999/12/31", "2001/10/02"};
    List<Timestamp> t = Query.select(dates, a -> DateUtils.parse(a));
    Approvals.verifyAll("i", Query.orderBy(t, Order.Descending, a -> a));
  }
}
