package com.spun.util.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.sql.Statement;

import com.spun.util.DatabaseConfiguration;
import com.spun.util.DatabaseUtils;
import com.spun.util.MySystem;

public class DatabaseLifeCycleUtils
{
  /***********************************************************************/
  public static void backupDatabase(Statement stmt, String databaseName, DatabaseConfiguration config,
      String fileName) throws Exception
  {
    switch (config.type)
    {
      case DatabaseUtils.SQLSERVER2005 :
      case DatabaseUtils.SQLSERVER2000 :
      case DatabaseUtils.SQLSERVER :
        backupSQLServer(stmt, databaseName, fileName);
        break;
      case DatabaseUtils.POSTGRESQL :
        backupPostgreSQL(databaseName, config, fileName);
        break;
      case DatabaseUtils.MY_SQL :
        backupMySQL(databaseName, fileName);
        break;
      default :
        throw new Error("Unhandled database type: " + DatabaseUtils.getDatabaseType(config.type));
    }
  }
  /***********************************************************************/
  private static void backupMySQL(String databaseName, String fileName) throws Exception
  {
    File file = new File(fileName);
    if (!file.getParentFile().exists())
    {
      file.getParentFile().createNewFile();
    }
    String commandLine = "mysqldump -r " + fileName + " " + databaseName;
    Process process = Runtime.getRuntime().exec(commandLine);
    process.waitFor();
    if (process.exitValue() != 0) { throw new Error(extractError(commandLine, process.getErrorStream())); }
  }
  /***********************************************************************/
  private static void backupPostgreSQL(String databaseName, DatabaseConfiguration config, String fileName)
      throws Exception
  {
    String commandLine = null;
    try
    {
      File file = new File(fileName);
      if (!file.getParentFile().exists())
      {
        file.getParentFile().createNewFile();
      }
      if (System.getProperty("os.name").indexOf("Windows") >= 0)
      {
        commandLine = "pg_dump --clean --username=" + config.getUserName() + " --file=\""
            + file.getCanonicalPath() + "\" " + databaseName;
      }
      else
      {
        commandLine = "pg_dump --clean --file=" + file.getCanonicalPath() + " " + databaseName;
      }
      Process process = Runtime.getRuntime().exec(commandLine);
      // check for a password prompt
      if (getPasswordPrompt(process))
      {
        // send the password
        sendPassword(process, config.getPassword());
      }
      process.waitFor();
      if (process.exitValue() != 0) { throw new Error(extractError(commandLine, process.getErrorStream())); }
    }
    catch (IOException e)
    {
      MySystem.variable("CommandLine", commandLine);
      throw e;
    }
  }
  private static boolean getPasswordPrompt(Process process) throws Exception
  {
    InputStream error = process.getErrorStream();
    InputStream in = process.getInputStream();
    int TIMEOUT = 3;
    long timeOut = System.currentTimeMillis() + (TIMEOUT * 1000);
    StringBuffer prompt = new StringBuffer();
    while (System.currentTimeMillis() < timeOut)
    {
      if (in.available() == 0 && error.available() == 0)
      {
        Thread.sleep(500);
      }
      else
      {
        if (in.available() != 0)
        {
          prompt.append((char) in.read());
        }
        if (error.available() != 0)
        {
          prompt.append((char) error.read());
        }
        timeOut = System.currentTimeMillis() + (TIMEOUT * 1000);
      }
    }
    MySystem.variable("prompt", prompt.toString());
    return prompt.toString().startsWith("Password");
  }
  private static void sendPassword(Process process, String password) throws Exception
  {
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
    writer.write(password);
    writer.newLine();
    writer.flush();
    writer.close();
  }
  /***********************************************************************/
  private static void backupSQLServer(Statement stmt, String databaseName, String fileName) throws SQLException
  {
    String sql = "BACKUP DATABASE " + databaseName + " TO DISK = '" + fileName + "'";
    MySystem.query("BACKUP", sql);
    stmt.execute(sql);
  }
  /***********************************************************************/
  public static void restoreDatabase(Statement stmt, String databaseName, DatabaseConfiguration config,
      String fileName) throws Exception
  {
    switch (config.type)
    {
      case DatabaseUtils.SQLSERVER2005 :
      case DatabaseUtils.SQLSERVER2000 :
      case DatabaseUtils.SQLSERVER :
        restoreSQLServer(stmt, databaseName, fileName);
        break;
      case DatabaseUtils.POSTGRESQL :
        restorePostgreSQL(databaseName, config, fileName);
        break;
      case DatabaseUtils.MY_SQL :
        restoreMySQL(stmt, databaseName, fileName);
        break;
      default :
        throw new Error("Unhandled database type: " + DatabaseUtils.getDatabaseType(config.type));
    }
  }
  /***********************************************************************/
  private static void restoreMySQL(Statement stmt, String databaseName, String fileName) throws SQLException
  {
    String restoreCommand = "LOAD DATA INFILE '" + fileName + "' REPLACE ...";
    MySystem.query(restoreCommand);
    stmt.execute(restoreCommand);
  }
  /***********************************************************************/
  private static void restorePostgreSQL(String databaseName, DatabaseConfiguration config, String fileName)
      throws Error, Exception
  {
    String commandLine;
    if (System.getProperty("os.name").indexOf("Windows") >= 0)
    {
      commandLine = "psql -f " + fileName + " -U " + config.userName + " " + databaseName;
    }
    else
    {
      commandLine = "psql -f " + fileName + " " + databaseName;
    }
    MySystem.event("RUNNING : " + commandLine);
    Process process = Runtime.getRuntime().exec(commandLine);
    if (getPasswordPrompt(process))
    {
      sendPassword(process, config.getPassword());
    }
    Thread.sleep(2000);
    String string = null;
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    if (reader.ready())
    {
      while ((string = reader.readLine()) != null)
      {
        MySystem.variable(string);
      }
    }
    reader.close();
    reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
    if (reader.ready())
    {
      while ((string = reader.readLine()) != null)
      {
        MySystem.variable(string);
      }
    }
    process.waitFor();
    reader.close();
    if (process.exitValue() != 0) { throw new Error(extractError(commandLine, process.getErrorStream())); }
  }
  /***********************************************************************/
  private static void restoreSQLServer(Statement stmt, String databaseName, String fileName) throws SQLException
  {
    stmt.execute("USE master");
    String restoreCommand = "RESTORE DATABASE " + databaseName + " FROM DISK =  '" + fileName + "'";
    MySystem.query(restoreCommand);
    stmt.execute(restoreCommand);
    stmt.execute("USE " + databaseName);
  }
  /***********************************************************************/
  private static String extractError(String commandLine, InputStream error) throws Exception
  {
    /*
     Process whoami = Runtime.getRuntime().exec("whoami");
     whoami.waitFor();
     String userName = extractText(whoami.getInputStream());*/
    String errorText = extractText(error);
    return "Error Executing '" + commandLine + /*"' AS USER '" + userName + */"'- " + errorText;
  }
  /***********************************************************************/
  public static String extractText(InputStream inStream) throws IOException
  {
    StringBuffer errorBuffer = new StringBuffer();
    BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
    while (in.ready())
    {
      errorBuffer.append(in.readLine());
    }
    return errorBuffer.toString();
  }
  /***********************************************************************/
  public static void deleteTable(String tableName, int databaseType, Statement stmt) throws SQLException
  {
    switch (databaseType)
    {
      case DatabaseUtils.SQLSERVER2005 :
      case DatabaseUtils.SQLSERVER2000 :
      case DatabaseUtils.SQLSERVER :
        deleteSQLServerTable(tableName, stmt);
        break;
      case DatabaseUtils.POSTGRESQL :
        deletePostgreSQLTable(tableName, stmt);
        break;
      case DatabaseUtils.MY_SQL :
        deleteMySqlTable(tableName, stmt);
        break;
      default :
        throw new Error("Unhandled database type: " + DatabaseUtils.getDatabaseType(databaseType));
    }
  }
  /***********************************************************************/
  private static void deleteMySqlTable(String tableName, Statement stmt) throws SQLException
  {
    stmt.executeUpdate("TRUNCATE " + tableName);
  }
  /***********************************************************************/
  private static void deletePostgreSQLTable(String tableName, Statement stmt) throws SQLException
  {
    stmt.executeUpdate("DELETE FROM " + tableName);
    stmt.executeQuery("select setval('" + tableName + "_pkey_seq',1)");
  }
  /***********************************************************************/
  public static void resetTableIndex(String tableName, int databaseType, Statement stmt) throws SQLException
  {
    switch (databaseType)
    {
      case DatabaseUtils.SQLSERVER2005 :
      case DatabaseUtils.SQLSERVER2000 :
      case DatabaseUtils.SQLSERVER :
        break;
      case DatabaseUtils.POSTGRESQL :
        resetPostgreIndex(tableName, stmt);
        break;
      case DatabaseUtils.MY_SQL :
        break;
      default :
        throw new Error("Unhandled database type: " + DatabaseUtils.getDatabaseType(databaseType));
    }
  }
  /***********************************************************************/
  private static void resetPostgreIndex(String tableName, Statement stmt) throws SQLException
  {
    String sql = "select setval('" + tableName + "_pkey_seq',(select max(pkey) + 1 from " + tableName + "))";
    MySystem.query("reset index", sql);
    stmt.executeQuery(sql);
  }
  /***********************************************************************/
  private static void deleteSQLServerTable(String tableName, Statement stmt) throws SQLException
  {
    stmt.executeUpdate("DELETE FROM " + tableName);
    stmt.executeUpdate("DBCC CHECKIDENT('" + tableName + "', RESEED, 1)");
  }
  /***********************************************************************/
  /***********************************************************************/
}