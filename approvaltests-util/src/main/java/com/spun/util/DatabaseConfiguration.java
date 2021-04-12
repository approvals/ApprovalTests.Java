package com.spun.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import com.spun.util.timers.Counter;

/**
 * A static class of convence functions for database access
 **/
public class DatabaseConfiguration
{
  private boolean                                              inRollbackOnlyMode = false;
  public String                                                dataSourceName     = null;
  public String                                                driver             = null;
  public String                                                protocol           = null;
  public String                                                server             = null;
  public String                                                port               = null;
  public String                                                database           = null;
  public String                                                userName           = null;
  public String                                                password           = null;
  public int                                                   type               = 0;
  public String                                                wrapper            = null;
  private Counter                                              connectionCounter  = new Counter();
  private static HashMap<String, DatabaseConfigurationWrapper> wrappers           = new HashMap<String, DatabaseConfigurationWrapper>();
  public static void registerWrapper(String name, DatabaseConfigurationWrapper wrapper)
  {
    wrappers.put(name, wrapper);
  }
  public DatabaseConfiguration(DatabaseConfiguration config)
  {
    this.dataSourceName = config.dataSourceName;
    this.driver = config.driver;
    this.protocol = config.protocol;
    this.server = config.server;
    this.port = config.port;
    this.database = config.database;
    this.userName = config.userName;
    this.password = config.password;
    this.type = config.type;
    this.wrapper = config.wrapper;
  }
  public DatabaseConfiguration(String dataSourceName, String driver, String protocol, String server, String port,
      String database, String userName, String password, int type)
  {
    this.dataSourceName = dataSourceName;
    this.driver = driver;
    this.protocol = protocol;
    if (protocol != null && protocol.endsWith("://"))
    { throw new Error("protocol's shouldn't end with '://'"); }
    this.server = server;
    if (server != null && server.endsWith("/"))
    { throw new Error("server's shouldn't end with '/'"); }
    this.port = port;
    this.database = database;
    this.userName = userName;
    this.password = password;
    this.type = type;
  }
  public boolean isDataSource()
  {
    return StringUtils.isNonZero(dataSourceName);
  }
  /**
   * Convenience function.
   **/
  public Properties getProperties()
  {
    Properties props = new Properties();
    props.setProperty("connection", "direct");
    props.setProperty("jdbcDriver", this.driver);
    props.setProperty("jdbcURL", DatabaseUtils.makeURL(protocol, server, port, database, type));
    props.setProperty("user", this.userName);
    props.setProperty("password", this.password);
    return props;
  }
  public Connection makeConnection()
  {
    return makeConnection(database);
  }
  public Connection makeConnection(String databaseName)
  {
    connectionCounter.inc();
    Connection con = makeConnectionFromWrapper(databaseName);
    if (this.inRollbackOnlyMode)
    {
      try
      {
        DatabaseUtils.beginTransaction(con);
      }
      catch (SQLException e)
      {
        throw ObjectUtils.throwAsError(e);
      }
    }
    return con;
  }
  private Connection makeConnectionFromWrapper(String databaseName)
  {
    Connection con;
    if (wrapper == null)
    {
      con = DatabaseUtils.makeConnection(driver, protocol, server, port, databaseName, userName, password, type);
    }
    else
    {
      DatabaseConfigurationWrapper w = (DatabaseConfigurationWrapper) wrappers.get(wrapper);
      if (w == null)
      { throw new Error("No wrapper found for '" + wrapper + "' in " + wrappers.keySet()); }
      con = w.makeConnection(databaseName, this);
    }
    return con;
  }
  public Counter getConnectionCounter()
  {
    return connectionCounter;
  }
  public String toString()
  {
    String value = "com.spun.util.DatabaseConfiguration[";
    value += " dataSourceName = '" + dataSourceName + "'" + ",\n" + " database = '" + database + "'" + ",\n"
        + " driver = '" + driver + "'" + ",\n" + " password = '" + password + "'" + ",\n" + " port = '" + port
        + "'" + ",\n" + " protocol = '" + protocol + "'" + ",\n" + " server = '" + server + "'" + ",\n"
        + " type = " + type + ",\n" + " userName = '" + userName + "'" + "]";
    return value;
  }
  public String getDataSourceName()
  {
    return dataSourceName;
  }
  public String getDatabase()
  {
    return database;
  }
  public String getDriver()
  {
    return driver;
  }
  public String getPassword()
  {
    return password;
  }
  public String getProtocol()
  {
    return protocol;
  }
  public String getServer()
  {
    return server;
  }
  public int getType()
  {
    return type;
  }
  public String getUserName()
  {
    return userName;
  }
  public int getPort()
  {
    return NumberUtils.load(port, 0);
  }
  public void setWrapper(String wrapper)
  {
    this.wrapper = wrapper;
  }
  public void setRollbackOnlyMode()
  {
    this.inRollbackOnlyMode = true;
  }
}
