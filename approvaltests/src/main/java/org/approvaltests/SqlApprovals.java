package org.approvaltests;

import com.spun.util.persistence.SqlLoader;
import org.approvaltests.core.Options;
import org.approvaltests.writers.DefaultApprovalWriterFactory;
import org.approvaltests.writers.ResultSetApprovalWriter;

import java.sql.ResultSet;

public class SqlApprovals
{
  static
  {
    DefaultApprovalWriterFactory.addDefault(o -> o instanceof ResultSet,
        (c, o) -> new ResultSetApprovalWriter((ResultSet) c));
  }
  public static <T> void verify(SqlLoader<T> loader)
  {
    verify(loader, new Options());
  }

  public static <T> void verify(SqlLoader<T> loader, Options options)
  {
    Approvals.verify(new SqlLoader.ExecutableWrapper<T>(loader), options);
  }

  public static void verify(ResultSet rs)
  {
    verify(rs, new Options());
  }

  public static void verify(ResultSet rs, Options options)
  {
    Approvals.verify(options.createWriter(rs), options);
  }
}
