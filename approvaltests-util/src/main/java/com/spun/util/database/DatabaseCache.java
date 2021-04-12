package com.spun.util.database;

import java.sql.SQLException;
import java.sql.Statement;

public interface DatabaseCache<T extends DatabaseObject>
{
  /**
   * The type of object this cache holds. Caches should only hold 1 type of
   * DatabaseObject
   **/
  public Class<T> getObjectType();
  /**
   * The default LinkBack setting
   **/
  public boolean isLinkBackOn();
  /**
   * loads the cache from the database
   **/
  public void load(Statement stmt) throws SQLException;
  /**
   * clears the cache and reloads it from the database
   **/
  public void reset(Statement stmt) throws SQLException;
  /**
   * @return the object from the cache with the corresponding pkey
   **/
  public T get(int pkey);
}