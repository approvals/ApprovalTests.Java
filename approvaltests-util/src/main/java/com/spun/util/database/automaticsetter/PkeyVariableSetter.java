package com.spun.util.database.automaticsetter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.spun.util.DatabaseUtils;
import com.spun.util.database.AutomaticVariableSetter;
import com.spun.util.database.DatabaseObject;

public class PkeyVariableSetter implements AutomaticVariableSetter
{
  public static final PkeyVariableSetter INSTANCE = new PkeyVariableSetter();
  private PkeyVariableSetter()
  {
  }

  public void setFor(DatabaseObject forObject, int atStage, Statement stmt) throws SQLException
  {
    if (atStage == INSERT_COMPLETED)
    {
      // My_System.variable("datase type", stmt.getConnection().getMetaData().getDatabaseProductName());
      switch (DatabaseUtils.getDatabaseType(stmt.getConnection()))
      {
        case DatabaseUtils.SQLSERVER2005 :
        case DatabaseUtils.SQLSERVER2000 :
        case DatabaseUtils.SQLSERVER :
          loadBySQL(forObject, atStage, stmt);
          break;
        case DatabaseUtils.MY_SQL :
          loadBySequenceMySQL(forObject, stmt);
          break;
        case DatabaseUtils.POSTGRESQL :
          loadBySequence(forObject, atStage, stmt);
          break;
        default :
          loadByJDBC(forObject, atStage, stmt);
          break;
      }
      if (forObject.getPkey() == 0) { throw new Error("Couldn't retrieve a pkey for insert into table : "
          + forObject.getMetadata().getTableName()); }
    }
  }

  private void loadBySequenceMySQL(DatabaseObject forObject, Statement stmt) throws SQLException
  {
    setPkey(forObject, stmt, "SELECT LAST_INSERT_ID()");
  }

  private void loadBySequence(DatabaseObject forObject, int atStage, Statement stmt) throws SQLException
  {
    setPkey(forObject, stmt,
        "SELECT currval('" + forObject.getMetadata().getTableName() + "_pkey_seq')");
  }

  private void loadBySQL(DatabaseObject forObject, int atStage, Statement stmt) throws SQLException
  {
    setPkey(forObject, stmt, "SELECT @@IDENTITY");
  }

  private void setPkey(DatabaseObject forObject, Statement stmt, String sql) throws SQLException {
    try (ResultSet rs = stmt.executeQuery(sql)) {
      if (rs.next()) {
        forObject.setPkey(rs.getInt(1));
      }
    }
  }

  private void loadByJDBC(DatabaseObject forObject, int atStage, Statement stmt) throws SQLException
  {
    try (ResultSet rs = stmt.getGeneratedKeys()) {
      if (rs.next()) {
        forObject.setPkey(rs.getInt(1));
      }
    }
  }
}