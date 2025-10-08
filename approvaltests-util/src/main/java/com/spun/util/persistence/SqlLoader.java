package com.spun.util.persistence;

import com.spun.util.ObjectUtils;
import com.spun.util.database.ResultSetWriter;
import com.spun.util.database.SQLQuery;
import com.spun.util.database.SQLStatementUtils;

import java.sql.ResultSet;
import java.sql.Statement;

public interface SqlLoader<T> extends Loader<T>
{
  public static class ExecutableWrapper<T> implements ExecutableCommand
  {
    private SqlLoader<T> loader;
    public ExecutableWrapper(SqlLoader<T> loader)
    {
      this.loader = loader;
    }

    @Override
    public String executeCommand(String command)
    {
      ResultSet sql;
      try
      {
        sql = SQLStatementUtils.executeQuery(command, loader.getStatement());
        return ResultSetWriter.toString(sql);
      }
      catch (Exception e)
      {
        throw ObjectUtils.throwAsError(e);
      }
    }

    @Override
    public String getCommand()
    {
      return loader.getQuery().toString();
    }
  }
  public SQLQuery getQuery();

  public Statement getStatement();
}
