package com.spun.util.database;

import com.spun.util.DatabaseConfiguration;
import com.spun.util.DatabaseUtils;
import com.spun.util.ObjectUtils;
import com.spun.util.logger.SimpleLogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseLifeCycleUtils
{
  public static void backupDatabase(Statement stmt, String databaseName, DatabaseConfiguration config,
      String fileName)
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
  private static void backupMySQL(String databaseName, String fileName)
  {
    try
    {
      File file = new File(fileName);
      if (!file.getParentFile().exists())
      {
        file.getParentFile().createNewFile();
      }
      String commandLine = "mysqldump -r " + fileName + " " + databaseName;
      Process process = Runtime.getRuntime().exec(commandLine);
      process.waitFor();
      if (process.exitValue() != 0)
      { throw new Error(extractError(commandLine, process.getErrorStream())); }
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  private static void backupPostgreSQL(String databaseName, DatabaseConfiguration config, String fileName)
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
        commandLine = "pg_dump --clean --username=" + config.getUserName() + " --file=\"" + file.getCanonicalPath()
            + "\" " + databaseName;
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
      if (process.exitValue() != 0)
      { throw new Error(extractError(commandLine, process.getErrorStream())); }
    }
    catch (Exception e)
    {
      SimpleLogger.variable("CommandLine", commandLine);
      throw ObjectUtils.throwAsError(e);
    }
  }
  private static boolean getPasswordPrompt(Process process)
  {
    try
    {
      StringBuffer prompt;
      try (InputStream error = process.getErrorStream())
      {
        try (InputStream in = process.getInputStream())
        {
          int TIMEOUT = 3;
          long timeOut = System.currentTimeMillis() + (TIMEOUT * 1000);
          prompt = new StringBuffer();
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
        }
      }
      SimpleLogger.variable("prompt", prompt.toString());
      return prompt.toString().startsWith("Password");
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  private static void sendPassword(Process process, String password)
  {
    try
    {
      OutputStreamWriter out = new OutputStreamWriter(process.getOutputStream(), StandardCharsets.UTF_8);
      try (BufferedWriter writer = new BufferedWriter(out))
      {
        writer.write(password);
        writer.newLine();
        writer.flush();
      }
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  private static void backupSQLServer(Statement stmt, String databaseName, String fileName)
  {
    String sql = "BACKUP DATABASE " + databaseName + " TO DISK = '" + fileName + "'";
    SimpleLogger.query("BACKUP", sql);
    ObjectUtils.throwAsError(() -> stmt.execute(sql));
  }
  public static void restoreDatabase(Statement stmt, String databaseName, DatabaseConfiguration config,
      String fileName)
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
  private static void restoreMySQL(Statement stmt, String databaseName, String fileName)
  {
    String restoreCommand = "LOAD DATA INFILE '" + fileName + "' REPLACE ...";
    SimpleLogger.query(restoreCommand);
    ObjectUtils.throwAsError(() -> stmt.execute(restoreCommand));
  }
  private static void restorePostgreSQL(String databaseName, DatabaseConfiguration config, String fileName)
  {
    try
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
      SimpleLogger.event("RUNNING : " + commandLine);
      Process process = Runtime.getRuntime().exec(commandLine);
      if (getPasswordPrompt(process))
      {
        sendPassword(process, config.getPassword());
      }
      Thread.sleep(2000);
      String string = null;
      InputStreamReader in = new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8);
      try (BufferedReader reader = new BufferedReader(in))
      {
        if (reader.ready())
        {
          while ((string = reader.readLine()) != null)
          {
            SimpleLogger.variable(string);
          }
        }
      }
      try (BufferedReader reader = new BufferedReader(
          new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8)))
      {
        if (reader.ready())
        {
          while ((string = reader.readLine()) != null)
          {
            SimpleLogger.variable(string);
          }
        }
        process.waitFor();
      }
      if (process.exitValue() != 0)
      { throw new Error(extractError(commandLine, process.getErrorStream())); }
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  private static void restoreSQLServer(Statement stmt, String databaseName, String fileName)
  {
    try
    {
      stmt.execute("USE master");
      String restoreCommand = "RESTORE DATABASE " + databaseName + " FROM DISK =  '" + fileName + "'";
      SimpleLogger.query(restoreCommand);
      stmt.execute(restoreCommand);
      stmt.execute("USE " + databaseName);
    }
    catch (SQLException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  private static String extractError(String commandLine, InputStream error)
  {
    String errorText = extractText(error);
    return "Error Executing '" + commandLine + /*"' AS USER '" + userName + */"'- " + errorText;
  }
  public static String extractText(InputStream inStream)
  {
    try
    {
      StringBuffer errorBuffer = new StringBuffer();
      InputStreamReader isr = new InputStreamReader(inStream, StandardCharsets.UTF_8);
      try (BufferedReader in = new BufferedReader(isr))
      {
        while (in.ready())
        {
          errorBuffer.append(in.readLine());
        }
      }
      return errorBuffer.toString();
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  public static void deleteTable(String tableName, int databaseType, Statement stmt)
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
  private static void deleteMySqlTable(String tableName, Statement stmt)
  {
    ObjectUtils.throwAsError(() -> stmt.executeUpdate("TRUNCATE " + tableName));
  }
  private static void deletePostgreSQLTable(String tableName, Statement stmt)
  {
    try
    {
      stmt.executeUpdate("DELETE FROM " + tableName);
      try (ResultSet resultSet = stmt.executeQuery("select setval('" + tableName + "_pkey_seq',1)"))
      {
      }
    }
    catch (SQLException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  public static void resetTableIndex(String tableName, int databaseType, Statement stmt)
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
  private static void resetPostgreIndex(String tableName, Statement stmt)
  {
    String sql = "select setval('" + tableName + "_pkey_seq',(select max(pkey) + 1 from " + tableName + "))";
    SimpleLogger.query("reset index", sql);
    try (ResultSet resultSet = stmt.executeQuery(sql))
    {
    }
    catch (SQLException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  private static void deleteSQLServerTable(String tableName, Statement stmt)
  {
    try
    {
      stmt.executeUpdate("DELETE FROM " + tableName);
      stmt.executeUpdate("DBCC CHECKIDENT('" + tableName + "', RESEED, 1)");
    }
    catch (SQLException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
}
