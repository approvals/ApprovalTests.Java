package com.spun.util.database;

public interface Syncable<T extends DatabaseObject>
{
  public void sync(DatabaseCache<T> cache);
}
