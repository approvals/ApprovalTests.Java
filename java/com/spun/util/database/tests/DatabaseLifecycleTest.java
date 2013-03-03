package com.spun.util.database.tests;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import junit.framework.TestCase;
import com.spun.util.DatabaseConfiguration;
import com.spun.util.NumberUtils;

public class DatabaseLifecycleTest extends TestCase
{
  private static String DATABASE1 = "dataone";
  private static String DATABASE2 = "datatwo";
  private static String FILE = null;

  public void testNothing() throws Exception
  {
  }

  /***********************************************************************
   * public void ptestLifeCycle() throws Exception { Connection con = null;
   * DatabaseConfiguration config = null; FILE =
   * FileUtils.createTempDirectory().getAbsolutePath() + File.separator +
   * "database.bak"; try { MySystem.toggleAll(true); //Create Database config =
   * Config.DATABASE_CONFIGURATION; DatabaseInfo info = createDatabase(config,
   * DATABASE1, true); // Backup and Destroy Database con =
   * config.makeConnection(); Statement stmt = con.createStatement();
   * DatabaseLifeCycleUtils.backupDatabase(stmt, info.databaseName,
   * Config.DATABASE_CONFIGURATION, FILE); dropDatabase(config, DATABASE1); if
   * (Config.DATABASE_TYPE == DatabaseUtils.POSTGRESQL) { createDatabase(config,
   * DATABASE2, false); } DatabaseLifeCycleUtils.restoreDatabase(stmt,
   * DATABASE2, Config.DATABASE_CONFIGURATION, FILE); stmt.close(); con.close();
   * assertDataIntegrity(config, DATABASE2, info); } finally { try {
   * tearDown(config); } catch (SQLException e) {
   * MySystem.warning("Failed in tearDown", e); } } } /
   ***********************************************************************/
  private void tearDown(DatabaseConfiguration config) throws SQLException
  {
    if (FILE != null)
    {
      new File(FILE).delete();
    }
    // dropDatabase(config, DATABASE1);
    dropDatabase(config, DATABASE2);
  }

  /***********************************************************************/
  private void dropDatabase(DatabaseConfiguration config, String database)
      throws SQLException
  {
    Connection con = config.makeConnection();
    Statement stmt = con.createStatement();
    stmt.execute("DROP DATABASE " + database);
    stmt.close();
    con.close();
  }

  /***********************************************************************/
  private void assertDataIntegrity(DatabaseConfiguration config,
      String database, DatabaseInfo info) throws SQLException
  {
    Connection con = null;
    try
    {
      con = config.makeConnection(database);
      Statement stmt = con.createStatement();
      ResultSet rs = stmt
          .executeQuery("SELECT COUNT(*) FROM " + info.tableName);
      rs.next();
      assertEquals("rows", info.rowData, rs.getInt(1));
      rs.close();
      stmt.close();
    } finally
    {
      con.close();
    }
  }

  /***********************************************************************/
  private DatabaseInfo createDatabase(DatabaseConfiguration config,
      String databaseName, boolean createTable) throws SQLException
  {
    DatabaseInfo info = new DatabaseInfo(databaseName, 13);
    if (createTable)
    {
      info.setTable("fruit", new String[] { "name", "tasty_factor" });
    }
    info.create(config);
    return info;
  }

  /***********************************************************************/
  /***********************************************************************/
  public static class DatabaseInfo
  {
    public String databaseName;
    private String[] tableRows;
    private String tableName;
    public int rowData = 1;

    /***********************************************************************/
    public DatabaseInfo(String databaseName, int rowData)
    {
      this.databaseName = databaseName;
      this.rowData = rowData;
    }

    /***********************************************************************/
    public void create(DatabaseConfiguration config) throws SQLException
    {
      Connection con = config.makeConnection();
      Statement stmt = con.createStatement();
      stmt.execute("CREATE DATABASE " + databaseName);
      stmt.close();
      con.close();
      con = config.makeConnection(databaseName);
      if (tableName != null)
      {
        stmt = con.createStatement();
        String createQuery = "CREATE TABLE " + tableName + "( ";
        for (int i = 0; i < tableRows.length; i++)
        {
          createQuery += tableRows[i] + " varchar (50) NULL, ";
        }
        createQuery = createQuery.substring(0, createQuery.length() - 2) + ")";
        stmt.execute(createQuery);
        // data
        String rows = createRowText(tableRows);
        for (int i = 0; i < rowData; i++)
        {
          stmt.execute("INSERT INTO " + tableName + rows + " VALUES "
              + createRowValueText(tableRows.length));
        }
        stmt.close();
      }
      con.close();
    }

    /***********************************************************************/
    private String createRowValueText(int number)
    {
      String createQuery = "(";
      for (int i = 0; i < number; i++)
      {
        createQuery += "'" + NumberUtils.createRandomStringOfNumbers(34)
            + "', ";
      }
      createQuery = createQuery.substring(0, createQuery.length() - 2) + ")";
      return createQuery;
    }

    /***********************************************************************/
    private String createRowText(String[] tableRows)
    {
      String createQuery = "(";
      for (int i = 0; i < tableRows.length; i++)
      {
        createQuery += tableRows[i] + ", ";
      }
      createQuery = createQuery.substring(0, createQuery.length() - 2) + ")";
      return createQuery;
    }

    /***********************************************************************/
    public void fillDatabase(Connection con)
    {
    }

    /***********************************************************************/
    public void setTable(String tableName, String[] tableRows)
    {
      this.tableName = tableName;
      this.tableRows = tableRows;
    }
  }
}
