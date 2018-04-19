package com.spun.util.persistence;

import java.sql.ResultSet;
import java.sql.Statement;

import com.spun.util.ObjectUtils;
import com.spun.util.database.ResultSetWriter;
import com.spun.util.database.SQLQuery;
import com.spun.util.database.SQLStatementUtils;

public interface SqlLoader<T> extends Loader<T>
{
  public static class ExecutableWrapper<T> implements ExecutableQuery
  {
    private SqlLoader<T> loader;
    public ExecutableWrapper(SqlLoader<T> loader)
    {
      this.loader = loader;
    }
    @Override
    public String executeQuery(String query)
    {
      ResultSet sql;
      try
      {
        sql = SQLStatementUtils.executeQuery(query, loader.getStatement());
        return ResultSetWriter.toString(sql);
      }
      catch (Exception e)
      {
        throw ObjectUtils.throwAsError(e);
      }
    }
    @Override
    public String getQuery()
    {
      return loader.getQuery().toString();
    }
  }
  public SQLQuery getQuery();
  public Statement getStatement();
}
