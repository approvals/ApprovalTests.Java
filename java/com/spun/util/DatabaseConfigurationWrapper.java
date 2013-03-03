package com.spun.util;

import java.sql.Connection;

/***********************************************************************/
public interface DatabaseConfigurationWrapper
{
  /***********************************************************************/
  public Connection makeConnection(String databaseName, DatabaseConfiguration configuration);
  /***********************************************************************/
  /***********************************************************************/
}
