package com.spun.util;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class MockConnection implements Connection
{
  public int autoCommitCount = 0;
  public int commitCount     = 0;
  public int getHoldability()
  {
    return 0;
  }

  public int getTransactionIsolation()
  {
    return 0;
  }

  public void clearWarnings()
  {
  }

  public void close()
  {
  }

  public void commit()
  {
    commitCount++;
  }

  public void rollback()
  {
  }

  public boolean getAutoCommit()
  {
    return false;
  }

  public boolean isClosed()
  {
    return false;
  }

  public boolean isReadOnly()
  {
    return false;
  }

  public void setHoldability(int arg0)
  {
  }

  public void setTransactionIsolation(int arg0)
  {
  }

  public void setAutoCommit(boolean arg0)
  {
    autoCommitCount++;
  }

  public void setReadOnly(boolean arg0)
  {
  }

  public String getCatalog()
  {
    return null;
  }

  public void setCatalog(String arg0)
  {
  }

  public DatabaseMetaData getMetaData()
  {
    return null;
  }

  public SQLWarning getWarnings()
  {
    return null;
  }

  public Savepoint setSavepoint()
  {
    return null;
  }

  public void releaseSavepoint(Savepoint arg0)
  {
  }

  public void rollback(Savepoint arg0)
  {
  }

  public Statement createStatement()
  {
    return null;
  }

  public Statement createStatement(int arg0, int arg1)
  {
    return null;
  }

  public Statement createStatement(int arg0, int arg1, int arg2)
  {
    return null;
  }

  public Map<String, Class<?>> getTypeMap()
  {
    return null;
  }

  public String nativeSQL(String arg0)
  {
    return null;
  }

  public CallableStatement prepareCall(String arg0)
  {
    return null;
  }

  public CallableStatement prepareCall(String arg0, int arg1, int arg2)
  {
    return null;
  }

  public CallableStatement prepareCall(String arg0, int arg1, int arg2, int arg3)
  {
    return null;
  }

  public PreparedStatement prepareStatement(String arg0)
  {
    return null;
  }

  public PreparedStatement prepareStatement(String arg0, int arg1)
  {
    return null;
  }

  public PreparedStatement prepareStatement(String arg0, int arg1, int arg2)
  {
    return null;
  }

  public PreparedStatement prepareStatement(String arg0, int arg1, int arg2, int arg3)
  {
    return null;
  }

  public PreparedStatement prepareStatement(String arg0, int[] arg1)
  {
    return null;
  }

  public Savepoint setSavepoint(String arg0)
  {
    return null;
  }

  public PreparedStatement prepareStatement(String arg0, String[] arg1)
  {
    return null;
  }

  public Array createArrayOf(String arg0, Object[] arg1)
  {
    return null;
  }

  public Blob createBlob()
  {
    return null;
  }

  public Clob createClob()
  {
    return null;
  }

  public NClob createNClob()
  {
    return null;
  }

  public SQLXML createSQLXML()
  {
    return null;
  }

  public Struct createStruct(String arg0, Object[] arg1)
  {
    return null;
  }

  public Properties getClientInfo()
  {
    return null;
  }

  public String getClientInfo(String arg0)
  {
    return null;
  }

  public boolean isValid(int arg0)
  {
    return false;
  }

  public void setClientInfo(Properties arg0)
  {
  }

  public void setClientInfo(String arg0, String arg1)
  {
  }

  public void setTypeMap(Map<String, Class<?>> arg0)
  {
  }

  public boolean isWrapperFor(Class<?> arg0)
  {
    return false;
  }

  public <T> T unwrap(Class<T> arg0)
  {
    return null;
  }

  public void abort(Executor executor)
  {
  }

  public int getNetworkTimeout()
  {
    return 0;
  }

  public String getSchema()
  {
    return null;
  }

  public void setNetworkTimeout(Executor executor, int milliseconds)
  {
  }

  public void setSchema(String schema)
  {
  }
}
