package org.approvaltests.writers;

import com.spun.util.Tuple;
import org.lambda.functions.Function1;
import org.lambda.query.Queryable;

import java.io.File;
import java.sql.ResultSet;

public class DefaultApprovalWriterFactory
{
  private static Queryable<Tuple<Function1<Object, Boolean>, ApprovalWriterFactory>> factories = new Queryable<>();
  static
  {
    addDefault(o -> o instanceof ResultSet, (c, o) -> new ResultSetApprovalWriter((ResultSet) c));
    addDefault(o -> o instanceof File, (c, o) -> new FileApprovalWriter((File) c));
    addDefault(o -> o instanceof ResultSet, (c, o) -> new ResultSetApprovalWriter((ResultSet) c));
  }
  public static ApprovalWriterFactory getDefaultFactory()
  {
    return (c, o) -> {
      Tuple<Function1<Object, Boolean>, ApprovalWriterFactory> first = factories.first(t -> t.getFirst().call(c));
      if (first != null)
      {
        return first.getSecond().create(c, o);
      }
      else
      {
        return ApprovalTextWriter.getFactory().create(c, o);
      }
    };
  }

  public static void addDefault(Function1<Object, Boolean> isValidFor, ApprovalWriterFactory factory)
  {
    factories.add(0, new Tuple<>(isValidFor, factory));
  }
}
