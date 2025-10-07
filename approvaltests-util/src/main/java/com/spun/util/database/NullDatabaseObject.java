package com.spun.util.database;

import java.sql.Statement;

public class NullDatabaseObject implements DatabaseObject
{
  @Override
  public boolean isNew()
  {
    return false;
  }

  @Override
  public void setNew(boolean b)
  {
  }

  @Override
  public int getPkey()
  {
    return 0;
  }

  @Override
  public boolean setPkey(int i)
  {
    return false;
  }

  @Override
  public Metadata getMetadata()
  {
    return null;
  }

  @Override
  public boolean save(Statement stmt)
  {
    return false;
  }

  @Override
  public boolean deleteFromDatabase(Statement stmt)
  {
    return false;
  }
}
