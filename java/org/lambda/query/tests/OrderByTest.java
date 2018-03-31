package org.lambda.query.tests;

import java.sql.Timestamp;
import java.util.List;

import org.approvaltests.Approvals;
import org.lambda.query.OrderBy.Order;
import org.lambda.query.Query;

import com.spun.util.DateUtils;

import junit.framework.TestCase;

public class OrderByTest extends TestCase
{
  public void testInts() throws Exception
  {
    Integer[] numbers = {6, 5, 8, 4, 9, 10};
    Approvals.verifyAll("i", Query.orderBy(numbers, a -> a));
  }
  public void testString() throws Exception
  {
    String[] names = {"Robert", "Lynn", "Samantha", "Marsha", "Llewellyn"};
    Approvals.verifyAll("i", Query.orderBy(names, a -> a));
  }
  public void testStringMethods() throws Exception
  {
    String[] names = {"Robert", "Lynn", "Samantha", "Marsha", "Llewellynn"};
    Approvals.verifyAll("i", Query.orderBy(names, a -> a.length()));
  }
  public void testDates() throws Exception
  {
    String[] dates = {"2010/05/21", "1975/06/28", "2000/01/01", "1999/12/31", "2001/10/02"};
    List<Timestamp> t = Query.select(dates, a -> DateUtils.parse(a));
    Approvals.verifyAll("i", Query.orderBy(t, Order.Descending, a -> a));
  }
}
