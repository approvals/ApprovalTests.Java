package com.spun.util.persistence;

import java.sql.SQLException;
import java.sql.Statement;
import com.spun.util.database.DatabaseObject;

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
