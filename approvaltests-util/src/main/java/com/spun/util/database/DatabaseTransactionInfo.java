package com.spun.util.database;

import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.SQLException;

import com.spun.util.ThreadUtils;

public class DatabaseTransactionInfo
{
  private WeakReference<Connection> connectionReference;
  private String                    originator;
  private boolean                   automaticCommit;

  /** 
   * returns the object from the cache with the corosponding pkey
   **/
  public DatabaseTransactionInfo(Connection con, int levelsOfRemoval) throws SQLException
  {
    this.automaticCommit = con.getAutoCommit();
    this.connectionReference = new WeakReference<Connection>(con);
    this.originator = getOriginatorText(levelsOfRemoval + 1);
    //My_System.variable("getOriginatorText for creation", originator + con.toString());
  }
  
  public static String getOriginatorText(int offset)
  {
    StackTraceElement trace[] = ThreadUtils.getStackTrace();
    offset = offset + 2;
    StackTraceElement element = trace[offset];
    int size = trace.length - offset;
    String text = "[" + size + "]" + element.getClassName() + "." + element.getMethodName();
    return text;
  }

  public boolean isFinalizeable()
  {
    return getConnection() == null;
  }

  public boolean isOriginator(Connection con, int levelsOfRemoval)
  {
    String originatorText = getOriginatorText(levelsOfRemoval + 1);
    return con.equals(getConnection()) && this.originator.equals(originatorText);
  }
  
  public Connection getConnection()
  {
    return this.connectionReference.get();
  }
  
  public void cleanConnection() throws SQLException
  {
    Connection con = getConnection();
    con.setAutoCommit(automaticCommit);
  }


}