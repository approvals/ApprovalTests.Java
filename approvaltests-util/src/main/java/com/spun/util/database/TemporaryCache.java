package com.spun.util.database;

import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TemporaryCache<T extends DatabaseObject> implements DatabaseCache<T>
{
  private Map<Integer, T> cache        = null;
  private Class<T>        defaultClass = null;
  private boolean         linkBack     = true;
  public TemporaryCache(T object)
  {
    this(object, true);
  }
  public TemporaryCache(T object, boolean linkBack)
  {
    this.linkBack = linkBack;
    if (object != null)
    {
      defaultClass = (Class<T>) object.getClass();
      cache = Collections.singletonMap(object.getPkey(), object);
    }
    else
    {
      cache = Collections.EMPTY_MAP;
    }
  }
  public TemporaryCache(T[] objects)
  {
    this(objects, true);
  }
  public TemporaryCache(T[] objects, boolean linkBack)
  {
    this.linkBack = linkBack;
    cache = new HashMap<Integer, T>(objects.length);
    for (int i = 0; i < objects.length; i++)
    {
      cache.put(objects[i].getPkey(), objects[i]);
      if (i == 0)
      {
        defaultClass = (Class<T>) objects[i].getClass();
      }
      else if (defaultClass != objects[i].getClass())
      {
        defaultClass = null;
      }
    }
  }
  public void forceGenericObjectType()
  {
    defaultClass = null;
  }
  public Class<T> getObjectType()
  {
    return defaultClass;
  }
  public boolean isLinkBackOn()
  {
    return linkBack;
  }
  /**
   * loads the chache from the database
   **/
  public synchronized void load(Statement stmt)
  {
    throw new UnsupportedOperationException("This only supports loaded objects");
  }
  /**
   * clears the cache and reloads it from the database
   **/
  public void reset(Statement stmt)
  {
    throw new UnsupportedOperationException("This only supports loaded objects");
  }
  /**
   * @return the object from the cache with the corosponding pkey
   **/
  public T get(int pkey)
  {
    return cache.get(pkey);
  }
}
