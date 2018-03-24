package com.spun.util.database;

import java.sql.Statement;  
import java.sql.SQLException;  

public final class DatabaseLoader
  implements DatabaseCache
{
  private static final String ERROR_TEXT = "DatabaseLoad is a Marker to Load from the Database";
  public static DatabaseCache INSTANCE = new DatabaseLoader();
  
  /**************************************************************************/
  private DatabaseLoader() {}
  /**************************************************************************/
  public static boolean isDatabaseLoader(DatabaseCache cache)
  {
    return (cache instanceof DatabaseLoader);
  }
  /**************************************************************************/
  public static boolean isNormalCache(DatabaseCache cache)
  {
    return !((cache == null) || (cache instanceof DatabaseLoader));
  }
  /**************************************************************************/
  public boolean equals(Object object)
  {
    return (object instanceof DatabaseLoader);
  }
  /**************************************************************************/
  /*                     UNSUPPORTED METHODS                                */
  /**************************************************************************/
  public Class<?> getObjectType()                           {throw new UnsupportedOperationException(ERROR_TEXT);}
  public boolean isLinkBackOn()                          {throw new UnsupportedOperationException(ERROR_TEXT);}
  public void load(Statement stmt)  throws SQLException  {throw new UnsupportedOperationException(ERROR_TEXT);}
  public void reset(Statement stmt) throws SQLException  {throw new UnsupportedOperationException(ERROR_TEXT);}
  public DatabaseObject get(int pkey)                    {throw new UnsupportedOperationException(ERROR_TEXT);}
  /**************************************************************************/
}