package org.lambda.query.tests;

import java.sql.Timestamp;
import java.util.List;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.lambda.functions.implementations.F1;
import org.lambda.query.OrderBy.Order;
import org.lambda.query.Query;

import com.spun.util.DateUtils;

public class OrderByTest extends TestCase
{
  public void testInts() throws Exception
  {
    Integer[] numbers = {6, 5, 8, 4, 9, 10};
    Approvals.verifyAll("i", Query.orderBy(numbers, new F1<Integer, Comparable>(0)
    {
      {
        ret(a);
      }
    }));
  }
  public void testString() throws Exception
  {
    String[] names = {"Robert", "Lynn", "Samantha", "Marsha", "Llewellyn"};
    Approvals.verifyAll("i", Query.orderBy(names, new F1<String, Comparable>("")
    {
      {
        ret(a);
      }
    }));
  }
  public void testStringMethods() throws Exception
  {
    String[] names = {"Robert", "Lynn", "Samantha", "Marsha", "Llewellynn"};
    F1<String, Comparable> f1 = new F1<String, Comparable>("")
    {
      {
        ret(a.length());
      }
    };
    Approvals.verifyAll("i", Query.orderBy(names, f1));
  }
  public void testDates() throws Exception
  {
    String[] dates = {"2010/05/21", "1975/06/28", "2000/01/01", "1999/12/31", "2001/10/02"};
    List<Timestamp> t = Query.select(dates, new F1<String, Timestamp>(dates[0])
    {
      {
        ret(DateUtils.parse(a));
      }
    });
    Approvals.verifyAll("i", Query.orderBy(t, Order.Descending, new F1<Timestamp, Comparable>(t.get(0))
    {
      {
        ret(a);
      }
    }));
  }
}
