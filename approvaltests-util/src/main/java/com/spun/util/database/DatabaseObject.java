package com.spun.util.database;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

public interface DatabaseObject
{
  public static DatabaseObject Null = new NullDatabaseObject();
  public boolean isNew();
  public void setNew(boolean b);
  public int getPkey();
  public boolean setPkey(int i);
  public Metadata getMetadata();
  public boolean save(Statement stmt) throws java.sql.SQLException;
  public boolean deleteFromDatabase(java.sql.Statement stmt) throws java.sql.SQLException;
  /**                     Inner Classes                                 **/
  public static class FilterNew implements com.spun.util.filters.Filter<DatabaseObject>
  {
    public static FilterNew INSTANCE = new FilterNew();
    public boolean isExtracted(DatabaseObject object) throws IllegalArgumentException
    {
      if (!(object instanceof DatabaseObject))
      {
        throw new IllegalArgumentException(
            "Expected Object of Type DatabaseObject but got " + object.getClass().getName());
      }
      DatabaseObject o = (DatabaseObject) object;
      return o.isNew();
    }
  }
  public static class Utils
  {
    public static void saveAll(DatabaseObject[] objects, Statement stmt) throws SQLException
    {
      java.util.HashSet<DatabaseObject> set = new java.util.HashSet<DatabaseObject>();
      for (int i = 0; i < objects.length; i++)
      {
        DatabaseRelationshipPersistent object = (DatabaseRelationshipPersistent) objects[i];
        object.save(stmt, set, false);
      }
    }
    public static <T extends DatabaseObject> T get(int pkey, T[] from)
    {
      if (from == null)
      { return null; }
      for (int i = 0; i < from.length; i++)
      {
        if (pkey == from[i].getPkey())
        { return from[i]; }
      }
      return null;
    }
    public static <T extends DatabaseObject> T get(int pkey, Collection<T> from)
    {
      for (T object : from)
      {
        if (pkey == object.getPkey())
        { return object; }
      }
      return null;
    }
  }
}