package org.approvaltests.writers;

import java.io.File;
import java.sql.ResultSet;

import org.approvaltests.awt.PaintableMultiFrame;
import org.approvaltests.awt.PaintableMultiframeWriter;
import org.lambda.functions.Function1;
import org.lambda.query.Queryable;

import com.spun.swing.Paintable;
import com.spun.util.Tuple;

public class DefaultApprovalWriterFactory
{
  private static Queryable<Tuple<Function1<Object, Boolean>, ApprovalWriterFactory>> factories = new Queryable<>();
  static
  {
    addDefault(o -> o instanceof ResultSet, (c, o) -> new ResultSetApprovalWriter((ResultSet) c));
  }
  public static ApprovalWriterFactory getDefaultFactory()
  {
    return (c, o) -> {
      Tuple<Function1<Object, Boolean>, ApprovalWriterFactory> first = factories
          .first(t -> t.getFirst().call(c));
      if (first != null)
      { return first.getSecond().create(c, o); }
      if (c instanceof File)
      {
        return new FileApprovalWriter((File) c);
      }
      else if (c instanceof Paintable)
      {
        return new PaintableApprovalWriter((Paintable) c);
      }
      else if (c instanceof PaintableMultiFrame)
      {
        return new PaintableMultiframeWriter((PaintableMultiFrame) c);
      }
      else
      {
        return ApprovalTextWriter.getFactory().create(c, o);
      }
    };
  }
  public static void addDefault(Function1<Object, Boolean> isValidFor, ApprovalWriterFactory factory)
  {
    factories.add(new Tuple<>(isValidFor, factory));
  }
}
