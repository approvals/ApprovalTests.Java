package com.spun.util.io;

/**
  * A static class of convence functions for Files
  **/
public class FTPConfig
{
  public static final int DEFUALT_FTP_PORT = 21;
  public String           host, userName, password;
  public int              port             = 0;
  public FTPConfig(String host, int port, String userName, String password)
  {
    this.host = host;
    this.port = port;
    this.userName = userName;
    this.password = password;
  }
}