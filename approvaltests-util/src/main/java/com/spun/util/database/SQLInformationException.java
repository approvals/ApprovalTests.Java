package com.spun.util.database;

import java.sql.SQLException;

public class SQLInformationException extends RuntimeException
{
  private static final long serialVersionUID = 1L;
  private String            query;
  private SQLException      exception;
  public SQLInformationException(String query, SQLException e)
  {
    this.exception = e;
    this.query = query;
  }

  public SQLException getException()
  {
    return exception;
  }

  public String getQuery()
  {
    return query;
  }

  public String getMessage()
  {
    return "The SQL :'" + query + "'\n" + "caused the exception :'" + exception.getMessage() + "'\n"
        + "Vendor code :'" + exception.getErrorCode() + "'\n" + "SQL state :'" + exception.getSQLState() + "'\n";
  }
}
