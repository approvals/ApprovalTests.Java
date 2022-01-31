package com.spun.util.persistence;

import com.spun.util.database.DatabaseObject;

import java.sql.Statement;

public class DatabaseObjectDeletor<T extends DatabaseObject> implements Deletor<T>
{
  private Statement stmt;
  public DatabaseObjectDeletor(Statement stmt)
  {
    this.stmt = stmt;
  }
  public void delete(T delete)
  {
    delete.deleteFromDatabase(stmt);
  }
}
