package com.spun.util.database;

import java.util.HashMap;

public class DatabaseLoadingTracker
{
  private HashMap<String, DatabaseObject> loaded = null;
  public DatabaseLoadingTracker()
  {
    loaded = new HashMap<String, DatabaseObject>();
  }

  private String getHashKey(DatabaseObject object)
  {
    return object.getClass().getName() + object.getPkey();
  }

  /**
    * loads the chache from the database
    **/
  public DatabaseObject load(DatabaseObject newObject)
  {
    if (newObject == null)
    { return null; }
    String hashKey = getHashKey(newObject);
    DatabaseObject oldObject = (DatabaseObject) loaded.get(hashKey);
    if (oldObject == null)
    {
      oldObject = newObject;
      loaded.put(hashKey, newObject);
    }
    else
    {
      //My_System.event("found duplicate of " + hashKey);
    }
    return oldObject;
  }
}