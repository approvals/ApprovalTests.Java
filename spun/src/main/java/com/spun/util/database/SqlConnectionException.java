package com.spun.util.database;

import java.sql.SQLException;

public class SqlConnectionException extends RuntimeException
{
  private static final long serialVersionUID = 1L;
  private String            driver;
  private String            protocol;
  private String            server;
  private String            port;
  private String            database;
  private String            userName;
  private String            url;
  public SqlConnectionException(String driver, String url, String protocol, String server, String port,
      String database, String userName, String password, int type, SQLException e)
  {
    super(e);
    this.driver = driver;
    this.url = url;
    this.protocol = protocol;
    this.server = server;
    this.port = port;
    this.database = database;
    this.userName = userName;
  }
  public String getMessage()
  {
    return "Problems Connecting to " + url + "\n" + "Message  : " + getCause().getMessage() + "\n" + "protocol  : "
        + protocol + "\n" + "Driver    : " + driver + "\n" + "Server    : " + server + "\n" + "Port      : " + port
        + "\n" + "Database  : " + database + "\n" + "UserName  : " + userName + "\n";
  }
}
