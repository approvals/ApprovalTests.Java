package com.spun.util.database;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import com.spun.util.filters.Filter;

public interface DatabaseObject
{
  public static DatabaseObject Null = new NullDatabaseObject();
  /***********************************************************************/
  public boolean isNew();
  public void setNew(boolean b);
  /***********************************************************************/
  public int getPkey();
  /***********************************************************************/
  public boolean setPkey(int i);
  /***********************************************************************/
  public Metadata getMetadata();
  /***********************************************************************/
  public boolean save(Statement stmt) throws java.sql.SQLException;
  /***********************************************************************/
  public boolean deleteFromDatabase(java.sql.Statement stmt) throws java.sql.SQLException;
  /***********************************************************************/
  /**                     Inner Classes                                 **/
  /***********************************************************************/
  public static class FilterNew implements com.spun.util.filters.Filter
  {
    public static Filter INSTANCE = new FilterNew();
    public boolean isExtracted(Object object) throws IllegalArgumentException
    {
      if (!(object instanceof DatabaseObject)) { throw new IllegalArgumentException(
          "Expected Object of Type DatabaseObject but got " + object.getClass().getName()); }
      DatabaseObject o = (DatabaseObject) object;
      return o.isNew();
    }
  }
  /**************************************************************************/
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
    /***********************************************************************/
    public static <T extends DatabaseObject> T get(int pkey, T[] from)
    {
      if (from == null) { return null; }
      for (int i = 0; i < from.length; i++)
      {
        if (pkey == from[i].getPkey()) { return from[i]; }
      }
      return null;
    }
    /**************************************************************************/
    public static <T extends DatabaseObject> T get(int pkey, Collection<T> from)
    {
      for (T object : from)
      {
        if (pkey == object.getPkey()) { return object; }
      }
      return null;
    }
    /**************************************************************************/
    /**
     * A convenience function to turn a vector of com.spun.util.database.DatabaseObject objects
     * into an Array of the com.spun.util.database.DatabaseObject objects.
     * @param vectorOf a Vector of com.spun.util.database.DatabaseObject objects
     * @return the array of com.spun.util.database.DatabaseObject.
     * @throws Error if an element of vectorOf is not a com.spun.util.database.DatabaseObject object.
     **/
    public static com.spun.util.database.DatabaseObject[] toArray(java.util.Collection<DatabaseObject> vectorOf)
    {
      if (vectorOf == null) { return new com.spun.util.database.DatabaseObject[0]; }
      com.spun.util.database.DatabaseObject array[] = new com.spun.util.database.DatabaseObject[vectorOf.size()];
      java.util.Iterator<DatabaseObject> iterator = vectorOf.iterator();
      int i = 0;
      while (iterator.hasNext())
      {
        java.lang.Object rowObject = iterator.next();
        if (rowObject instanceof com.spun.util.database.DatabaseObject)
        {
          array[i++] = (com.spun.util.database.DatabaseObject) rowObject;
        }
        else
        {
          throw new Error("toArray[" + i + "] is not an instance of com.spun.util.database.DatabaseObject but a "
              + rowObject.getClass().getName());
        }
      }
      return array;
    }
    /************************************************************************/
    /************************************************************************/
  }
  /***********************************************************************/
  /***********************************************************************/
}