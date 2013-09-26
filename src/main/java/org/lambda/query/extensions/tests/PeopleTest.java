package org.lambda.query.extensions.tests;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import junit.framework.TestCase;

import org.lambda.actions.implementations.A1;
import org.lambda.functions.implementations.F1;
import org.lambda.functions.implementations.S1;
import org.lambda.query.ParallelLoops;
import org.lambda.query.Query;
import org.lambda.query.extensions.Queryyy;

public class PeopleTest extends TestCase
{
  public void testBrainy() throws Exception
  {
    List<Person> people = getStudents();
    Person brainy = (Person) people.use(Queryyy.class).where(new F1<Person, Boolean>(Person.Null)
    {
      {
        ret(a.getYear() == 2009);
      }
    }).use(Queryyy.class).max(new F1<Person, Number>(Person.Null)
    {
      {
        ret(a.getGradePoint());
      }
    });
    System.out.println(brainy);
  }
  public void testBrainy2() throws Exception
  {
    List<Person> people = Query.where(getStudents(), new F1<Person, Boolean>(Person.Null)
    {
      {
        ret(a.getYear() == 2009);
      }
    });
    Person max = Query.max(people, new S1<Person>(Person.Null)
    {
      {
        ret(a.getGradePoint());
      }
    });
    System.out.println(max);
  }
  public void testBrainy3() throws Exception
  {
    final AtomicReference<Person> p = new AtomicReference<Person>(Person.Null);
    ParallelLoops.forEach(getStudents(), new A1<Person>(false, Person.Null, p)
    {
      {
        Thread.sleep(1000);
        if (b.getYear() == 2009 && b.getGradePoint() > p.get().getGradePoint())
        {
          p.set(b);
        }
      }
    });
    System.out.println(p.get());
  }
  public void testBrainy4() throws Exception
  {
    Person p = Person.Null;
    for (Person b : getStudents())
    {
      if (b.getYear() == 2009 && b.getGradePoint() > p.getGradePoint())
      {
        p = b;
      }
    }
    System.out.println(p);
  }
  public static List<Person> getStudents()
  {
    return Arrays.asList(new Person("Valarie", 2010, 4.0), new Person("Cristine", 2009, 3.2), new Person("Kari",
        2009, 3.6), new Person("Kim", 2009, 3.5), new Person("Jasper", 2009, 3.0), new Person("Edie", 2010, 3.3),
        new Person("Lynn", 2009, 3.8), new Person("Clara", 2009, 3.3), new Person("Christina", 2010, 3.4));
  }
}
