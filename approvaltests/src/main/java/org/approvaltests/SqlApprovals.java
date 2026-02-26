package org.approvaltests;

import com.spun.util.persistence.SqlLoader;
import org.approvaltests.core.Options;

import java.sql.ResultSet;

public class SqlApprovals
{
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
