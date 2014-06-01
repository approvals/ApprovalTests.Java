package com.spun.util.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.spun.util.DatabaseUtils;

public class SQLStatementUtils
{
  /***********************************************************************/
  public static ResultSet executeQuery(SQLQuery query, Statement stmt) throws SQLException
  {
    return executeQuery(query.toString(DatabaseUtils.getDatabaseType(stmt)), stmt); 
  }
  /***********************************************************************/
  public static ResultSet executeQuery(String query, Statement stmt) throws SQLInformationException
  {
    try
    {
      //My_System.query(query);
      return stmt.executeQuery(query);
    }
    catch (SQLException e)
    {
      throw new SQLInformationException(query, e);
    }
  }
  /***********************************************************************/
  public static int executeUpdate(String query, Statement stmt) throws SQLInformationException
  {
    try
    {
      return stmt.executeUpdate(query);
    }
    catch (SQLException e)
    {
      throw new SQLInformationException(query, e);
    }
  }
  /***********************************************************************/
  public static boolean execute(String query, Statement stmt) throws SQLInformationException
  {
    try
    {
      return stmt.execute(query);
    }
    catch (SQLException e)
    {
      throw new SQLInformationException(query, e);
    }
  }
  /***********************************************************************/
  /***********************************************************************/
}