package com.spun.util.database;

import java.sql.Statement;

public interface DatabaseRelationshipPersistent
{
  public boolean save(Statement stmt, java.util.HashSet<com.spun.util.database.DatabaseObject> set,
      boolean shallowOnly);
}
