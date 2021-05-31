package org.approvaltests.writers;

import com.spun.swing.Paintable;
import java.io.File;
import java.sql.ResultSet;

import org.approvaltests.awt.PaintableMultiFrame;
import org.approvaltests.awt.PaintableMultiframeWriter;
import org.approvaltests.core.ApprovalWriter;
import org.approvaltests.core.Options;

public interface ApprovalWriterFactory
{
  public ApprovalWriter create(Object content, Options options);
  static ApprovalWriterFactory getDefaultFactory()
  {
    return (c, o) -> {
      if (c instanceof ResultSet)
      {
        return new ResultSetApprovalWriter((ResultSet) c);
      }
      else if (c instanceof File)
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
}
