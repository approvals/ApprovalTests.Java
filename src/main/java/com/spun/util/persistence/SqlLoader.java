package com.spun.util.persistence;

import java.sql.ResultSet;
import java.sql.Statement;

import com.spun.util.database.ResultSetWriter;
import com.spun.util.database.SQLQuery;
import com.spun.util.database.SQLStatementUtils;

public interface SqlLoader<T> extends Loader<T>
{
  public static class ExecutableWrapper implements ExecutableQuery
  {
    private SqlLoader loader;
    public ExecutableWrapper(SqlLoader loader)
    {
      this.loader = loader;
    }
    @Override
    public String executeQuery(String query) throws Exception
    {
      ResultSet sql = SQLStatementUtils.executeQuery(query, loader.getStatement());
      return ResultSetWriter.toString(sql);
    }
    @Override
    public String getQuery() throws Exception
    {
      return loader.getQuery().toString();
    }
  }
  public SQLQuery getQuery() throws Exception;
  public Statement getStatement() throws Exception;
}
