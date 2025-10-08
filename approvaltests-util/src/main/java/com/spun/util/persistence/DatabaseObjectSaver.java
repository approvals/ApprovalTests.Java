package com.spun.util.persistence;

import com.spun.util.database.DatabaseObject;

import java.sql.Statement;

public class DatabaseObjectSaver<T extends DatabaseObject> implements Saver<T>
{
  private Statement stmt;
  public DatabaseObjectSaver(Statement stmt)
  {
    this.stmt = stmt;
  }

  public DatabaseObject save(DatabaseObject save)
  {
    save.save(stmt);
    return save;
  }
}
