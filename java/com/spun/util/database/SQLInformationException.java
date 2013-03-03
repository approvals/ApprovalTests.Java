package com.spun.util.database;

import java.sql.SQLException;

public class SQLInformationException extends SQLException
{
  private static class SQL_SERVER_CODES
  {
    //1205 - deadlock
    //170 - incorrect syntax
  }
  private String       query;
  private SQLException exception;
  /***********************************************************************/
  public SQLInformationException(String query, SQLException e)
  {
    this.exception = e;
    this.query = query;
  }
  /***********************************************************************/
  public SQLException getException()
  {
    return exception;
  }
  /***********************************************************************/
  public String getQuery()
  {
    return query;
  }
  /***********************************************************************/
   public String getMessage()
  {
     return "The SQL :'" + query + "'\n" + 
            "caused the exception :'" + exception.getMessage() + "'\n" +
            "Vendor code :'" + exception.getErrorCode() + "'\n" +
            "SQL state :'" + exception.getSQLState() + "'\n" ;
  }
  
  /***********************************************************************/
  /***********************************************************************/
}
