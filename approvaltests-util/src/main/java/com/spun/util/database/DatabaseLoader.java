package com.spun.util.database;

import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseLoader<T extends DatabaseObject> implements DatabaseCache<T>
{
  private static final String                 ERROR_TEXT = "DatabaseLoad is a Marker to Load from the Database";
  public static DatabaseCache<DatabaseObject> INSTANCE   = new DatabaseLoader<DatabaseObject>();

  private DatabaseLoader()
  {
  }

  public static <T extends DatabaseObject> boolean isDatabaseLoader(DatabaseCache<T> cache)
  {
    return (cache instanceof DatabaseLoader);
  }

  public static <T extends DatabaseObject> boolean isNormalCache(DatabaseCache<T> cache)
  {
    return !((cache == null) || (cache instanceof DatabaseLoader));
  }

  public boolean equals(Object object)
  {
    return (object instanceof DatabaseLoader);
  }

  /*                     UNSUPPORTED METHODS                                */

  public Class<T> getObjectType()
  {
    throw new UnsupportedOperationException(ERROR_TEXT);
  }
  public boolean isLinkBackOn()
  {
    throw new UnsupportedOperationException(ERROR_TEXT);
  }
  public void load(Statement stmt) throws SQLException
  {
    throw new UnsupportedOperationException(ERROR_TEXT);
  }
  public void reset(Statement stmt) throws SQLException
  {
    throw new UnsupportedOperationException(ERROR_TEXT);
  }
  public T get(int pkey)
  {
    throw new UnsupportedOperationException(ERROR_TEXT);
  }

}