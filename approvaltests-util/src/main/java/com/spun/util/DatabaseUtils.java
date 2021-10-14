package com.spun.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import com.spun.util.database.DatabaseObject;
import com.spun.util.database.DatabaseTransactionInfo;
import com.spun.util.database.SqlConnectionException;
import com.spun.util.logger.SimpleLogger;
import sun.java2d.pipe.SpanShapeRenderer;

/**
 * A static class of convenience functions for database access
 */
public class DatabaseUtils
{
  private static ArrayList<DatabaseTransactionInfo> connections      = new ArrayList<DatabaseTransactionInfo>();
  public static final int                           NEW              = 1;
  public static final int                           NEW_MODIFIED     = 2;
  public static final int                           OLD              = 3;
  public static final int                           OLD_MODIFIED     = 4;
  public static final int                           ACCESS           = 0;
  public static final int                           POSTGRESQL       = 1;
  public static final int                           SYBASE           = 2;
  public static final int                           ORACLE           = 3;
  public static final int                           SQLSERVER        = 4;
  public static final int                           SQLSERVER2000    = 5;
  public static final int                           MY_SQL           = 6;
  public static final int                           SQLSERVER2005    = 7;
  public static final String                        DATABASE_TYPES[] = {"Access",
                                                                        "PostgreSQL",
                                                                        "Sybase",
                                                                        "Oracle",
                                                                        "Microsoft SQL Server  7.00",
                                                                        "Microsoft SQL Server  2000",
                                                                        "MySQL",
                                                                        "Microsoft SQL Server"};
  public static String getDatabaseType(int type)
  {
    return DATABASE_TYPES[type];
  }
  public static int getDatabaseType(Statement stmt)
  {
    return ObjectUtils.throwAsError(() -> getDatabaseType(stmt.getConnection()));
  }
  public static int getDatabaseType(Connection con)
  {
    String dbName = ObjectUtils.throwAsError(() -> con.getMetaData().getDatabaseProductName());
    for (int i = 0; i < DATABASE_TYPES.length; i++)
    {
      if (DATABASE_TYPES[i].equalsIgnoreCase(dbName.trim()))
      { return i; }
    }
    throw new Error("Unrecognized database product name: " + dbName);
  }
  /**
   * Finds the database type name for a jdbc sql type
   */
  public static String findDatabaseName(int i)
  {
    String name = null;
    switch (i)
    {
      case java.sql.Types.BIT :
        name = "boolean";
        break;
      case java.sql.Types.CHAR :
        name = "char";
        break;
      case java.sql.Types.DECIMAL :
        name = "decimal";
        break;
      case java.sql.Types.DOUBLE :
        name = "numeric";
        break;
      case java.sql.Types.FLOAT :
        name = "numeric";
        break;
      case java.sql.Types.INTEGER :
        name = "integer";
        break;
      case java.sql.Types.NUMERIC :
        name = "numeric";
        break;
      case java.sql.Types.TIMESTAMP :
        name = "timestamp";
        break;
      case java.sql.Types.VARCHAR :
        name = "varchar";
        break;
      default :
        SimpleLogger.warning("The Type not found(" + i + ")");
        break;
    }
    return name;
  }
  /**
   * Finds the java object or primative for a sql type
   */
  public static String findSQLName(int i)
  {
    String name = null;
    switch (i)
    {
      case java.sql.Types.ARRAY :
        name = "unknown";
        break;
      case java.sql.Types.BIGINT :
        name = "long";
        break;
      case java.sql.Types.BINARY :
        name = "byte[]";
        break;
      case java.sql.Types.BIT :
        name = "boolean";
        break;
      case java.sql.Types.BLOB :
        name = "BLOB";
        break;
      case java.sql.Types.CHAR :
        name = "java.lang.String";
        break;
      case java.sql.Types.CLOB :
        name = "CLOB";
        break;
      case java.sql.Types.DATE :
        name = "java.sql.Date";
        break;
      case java.sql.Types.DECIMAL :
        name = "java.math.BigDecimal";
        break;
      case java.sql.Types.DISTINCT :
        name = "DISTINCT";
        break;
      case java.sql.Types.DOUBLE :
        name = "double";
        break;
      case java.sql.Types.FLOAT :
        name = "double";
        break;
      case java.sql.Types.INTEGER :
        name = "int";
        break;
      case java.sql.Types.JAVA_OBJECT :
        name = "unknown";
        break;
      case java.sql.Types.LONGVARBINARY :
        name = "byte[]";
        break;
      case java.sql.Types.LONGVARCHAR :
        name = "String";
        break;
      case java.sql.Types.NULL :
        name = "NULL";
        break;
      case java.sql.Types.NUMERIC :
        name = "java.math.BigDecimal";
        break;
      case java.sql.Types.OTHER :
        name = "OTHER";
        break;
      case java.sql.Types.REAL :
        name = "float";
        break;
      case java.sql.Types.REF :
        name = "REF";
        break;
      case java.sql.Types.SMALLINT :
        name = "short";
        break;
      case java.sql.Types.STRUCT :
        name = "STRUCT";
        break;
      case java.sql.Types.TIME :
        name = "java.sql.Time";
        break;
      case java.sql.Types.TIMESTAMP :
        name = "java.sql.Timestamp";
        break;
      case java.sql.Types.TINYINT :
        name = "byte";
        break;
      case java.sql.Types.VARBINARY :
        name = "byte[]";
        break;
      case java.sql.Types.VARCHAR :
        name = "java.lang.String";
        break;
      case 11 :
        name = "java.sql.Date";
        break;
      case -9 :
        name = "java.lang.String";
        break;
      default :
        SimpleLogger.warning("The Type not found(" + i + ")");
        printSQLValues();
        break;
    }
    return name;
  }
  public static boolean isSqlServer(int databaseType)
  {
    switch (databaseType)
    {
      case SQLSERVER2000 :
      case DatabaseUtils.SQLSERVER2005 :
      case DatabaseUtils.SQLSERVER :
        return true;
      default :
        return false;
    }
  }
  public static String makeSQL2000URL(String protocol, String server, String port, String database)
  {
    String theURL = "";
    protocol = StringUtils.isNonZero(protocol) ? protocol + "://" : "";
    server = StringUtils.isNonZero(server) ? server : "";
    port = StringUtils.isNonZero(port) ? ":" + port : "";
    database = StringUtils.isNonZero(database) ? ";DatabaseName=" + database : "";
    theURL = protocol + server + port + database + ";SelectMethod=cursor";
    return theURL;
  }
  /**
   * Creates a connection to the Database.
   */
  public static String makeMySqlURL(String protocol, String server, String port, String database)
  {
    String theURL = "";
    protocol = StringUtils.isNonZero(protocol) ? protocol + "://" : "";
    server = StringUtils.isNonZero(server) ? server : "";
    port = StringUtils.isNonZero(port) ? ":" + port : "";
    database = StringUtils.isNonZero(database) ? "/" + database : "";
    theURL = protocol + server + port + database;
    return theURL;
  }
  /**
   * Creates a connection to the Database, and stores it in the cookies table.
   */
  public static String makeURL(String protocol, String server, String port, String database, int type)
  {
    switch (type)
    {
      case ACCESS :
        return makeJDBCAccessURL(protocol, server, port, database);
      case SQLSERVER2000 :
      case DatabaseUtils.SQLSERVER2005 :
        return makeSQL2000URL(protocol, server, port, database);
      case ORACLE :
      case SQLSERVER :
      case POSTGRESQL :
      case SYBASE :
        return makeMySqlURL(protocol, server, port, database);
      case MY_SQL :
        return makeMySqlURL(protocol, server, port, database) + "?useUnicode=true&characterEncoding=UTF-8";
    }
    throw new Error("Database Type '" + type + "' not supported");
  }
  /**
   * Creates a connection to the Database. This is for Access, which is crap!
   */
  public static String makeJDBCAccessURL(String protocol, String server, String port, String database)
  {
    String theURL = "";
    database = (StringUtils.isNonZero(database)) ? ("" + database) : "";
    theURL = protocol + database;
    return theURL;
  }
  /**
   * Creates a connection to the Database.
   */
  public static Connection makeConnection(String driver, String protocol, String server, String port,
      String database, String userName, String password, int type)
  {
    Connection con = null;
    String theURL = null;
    try
    {
      driver = StringUtils.isNonZero(driver) ? driver : "sun.jdbc.odbc.JdbcOdbcDriver";
      theURL = makeURL(protocol, server, port, database, type);
      SimpleLogger.variable("URL = " + theURL);
      Class.forName(driver).newInstance();
      con = DriverManager.getConnection(theURL, userName, password);
    }
    catch (SQLException e)
    {
      throw new SqlConnectionException(driver, theURL, protocol, server, port, database, userName, password, type,
          e);
    }
    catch (Exception e)
    {
      SimpleLogger.warning("URL : " + theURL);
      ObjectUtils.throwAsError(e);
    }
    return con;
  }
  public static void printSQLValues()
  {
    SimpleLogger.variable("java.sql.Types.BIT           = " + java.sql.Types.BIT);
    SimpleLogger.variable("java.sql.Types.TINYINT       = " + java.sql.Types.TINYINT);
    SimpleLogger.variable("java.sql.Types.BIGINT        = " + java.sql.Types.BIGINT);
    SimpleLogger.variable("java.sql.Types.LONGVARBINARY = " + java.sql.Types.LONGVARBINARY);
    SimpleLogger.variable("java.sql.Types.VARBINARY     = " + java.sql.Types.VARBINARY);
    SimpleLogger.variable("java.sql.Types.BINARY        = " + java.sql.Types.BINARY);
    SimpleLogger.variable("java.sql.Types.LONGVARCHAR   = " + java.sql.Types.LONGVARCHAR);
    SimpleLogger.variable("java.sql.Types.NULL          = " + java.sql.Types.NULL);
    SimpleLogger.variable("java.sql.Types.CHAR          = " + java.sql.Types.CHAR);
    SimpleLogger.variable("java.sql.Types.NUMERIC       = " + java.sql.Types.NUMERIC);
    SimpleLogger.variable("java.sql.Types.DECIMAL       = " + java.sql.Types.DECIMAL);
    SimpleLogger.variable("java.sql.Types.INTEGER       = " + java.sql.Types.INTEGER);
    SimpleLogger.variable("java.sql.Types.SMALLINT      = " + java.sql.Types.SMALLINT);
    SimpleLogger.variable("java.sql.Types.FLOAT         = " + java.sql.Types.FLOAT);
    SimpleLogger.variable("java.sql.Types.REAL          = " + java.sql.Types.REAL);
    SimpleLogger.variable("java.sql.Types.DOUBLE        = " + java.sql.Types.DOUBLE);
    SimpleLogger.variable("java.sql.Types.VARCHAR       = " + java.sql.Types.VARCHAR);
    SimpleLogger.variable("java.sql.Types.DATE          = " + java.sql.Types.DATE);
    SimpleLogger.variable("java.sql.Types.TIME          = " + java.sql.Types.TIME);
    SimpleLogger.variable("java.sql.Types.TIMESTAMP     = " + java.sql.Types.TIMESTAMP);
    SimpleLogger.variable("java.sql.Types.OTHER         = " + java.sql.Types.OTHER);
    SimpleLogger.variable("java.sql.Types.JAVA_OBJECT   = " + java.sql.Types.JAVA_OBJECT);
    SimpleLogger.variable("java.sql.Types.DISTINCT      = " + java.sql.Types.DISTINCT);
    SimpleLogger.variable("java.sql.Types.STRUCT        = " + java.sql.Types.STRUCT);
    SimpleLogger.variable("java.sql.Types.ARRAY         = " + java.sql.Types.ARRAY);
    SimpleLogger.variable("java.sql.Types.BLOB          = " + java.sql.Types.BLOB);
    SimpleLogger.variable("java.sql.Types.CLOB          = " + java.sql.Types.CLOB);
    SimpleLogger.variable("java.sql.Types.REF           = " + java.sql.Types.REF);
  }
  public static String getMethodName(String databaseName)
  {
    return getVariableName(databaseName, true);
  }
  public static String getVariableName(String databaseName)
  {
    return getVariableName(databaseName, false);
  }
  public static String getVariableName(String databaseName, boolean capFirstLetter)
  {
    StringBuffer returning = new StringBuffer(databaseName.length());
    String upper = databaseName.toUpperCase();
    int place = 0;
    while (place < databaseName.length())
    {
      char letter = databaseName.charAt(place);
      if (letter == '_')
      {
        returning.append(upper.charAt(++place));
      }
      else if (place == 0 && capFirstLetter)
      {
        returning.append(upper.charAt(0));
      }
      else
      {
        returning.append(letter);
      }
      place++;
    }
    return returning.toString();
  }
  public static String formatNullableObject(Object o)
  {
    return formatNullableObject(o, POSTGRESQL);
  }
  public static String formatNullableObject(Object o, int type)
  {
    if (o == null)
    {
      return "null";
    }
    else
    {
      if (o instanceof Integer)
      {
        return o.toString();
      }
      else
      {
        String text = null;
        switch (type)
        {
          case DatabaseUtils.SQLSERVER2005 :
          case SQLSERVER2000 :
          case SQLSERVER :
            text = "'" + toEscapeMSSQL(o.toString()) + "'";
            break;
          case ACCESS :
            text = "'" + toEscapeACCESS_SQL(o.toString()) + "'";
            break;
          case ORACLE :
          case POSTGRESQL :
          case SYBASE :
            text = "'" + toEscapeSQL(o.toString()) + "'";
            break;
        }
        return text;
      }
    }
  }
  public static String toEscapeMSSQL(String unformattedString)
  {
    if (unformattedString.indexOf('\'') == -1)
    { return unformattedString; }
    StringBuffer b = new StringBuffer(unformattedString);
    for (int i = 0; i < b.length(); i++)
    {
      char c = b.charAt(i);
      switch (c)
      {
        case '\'' :
          b.insert(i, '\'');
          i++;
          break;
      }
    }
    return b.toString();
  }
  public static String toEscapeACCESS_SQL(String unformattedString)
  {
    if ((unformattedString.indexOf('\'') == -1) && (unformattedString.indexOf('\"') == -1)
        && (unformattedString.indexOf('\\') == -1))
    { return unformattedString; }
    StringBuffer b = new StringBuffer(unformattedString);
    for (int i = 0; i < b.length(); i++)
    {
      char c = b.charAt(i);
      switch (c)
      {
        case '\"' :
        case '\\' :
          b.insert(i, '\\');
          i++;
          break;
        case '\'' :
          b.insert(i, '\'');
          i++;
          break;
      }
    }
    return b.toString();
  }
  public static String toEscapeSQL(String unformattedString)
  {
    if ((unformattedString == null) || ((unformattedString.indexOf('\'') == -1)
        && (unformattedString.indexOf('\"') == -1) && (unformattedString.indexOf('\\') == -1)))
    { return unformattedString; }
    StringBuffer b = new StringBuffer(unformattedString);
    for (int i = 0; i < b.length(); i++)
    {
      char c = b.charAt(i);
      switch (c)
      {
        case '\"' :
        case '\\' :
        case '\'' :
          b.insert(i, '\\');
          i++;
          break;
      }
    }
    return b.toString();
  }
  public static void beginTransaction(Statement stmt)
  {
    ObjectUtils.throwAsError(() -> beginTransaction(stmt.getConnection(), 2));
  }
  public static void beginTransaction(Connection con)
  {
    beginTransaction(con, 2);
  }
  private static void beginTransaction(Connection con, int offset)
  {
    try
    {
      if (getConnection(connections, con) == null)
      {
        synchronized (connections)
        {
          SimpleLogger.event("Starting transaction " + DatabaseTransactionInfo.getOriginatorText(offset + 1));
          connections.add(new DatabaseTransactionInfo(con, 1 + offset));
        }
        con.setAutoCommit(false);
      }
      else
      {
        SimpleLogger.event("already exist");
      }
    }
    catch (SQLException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  private static DatabaseTransactionInfo getConnection(ArrayList<DatabaseTransactionInfo> connections2,
      Connection con)
  {
    for (DatabaseTransactionInfo info : connections2)
    {
      if (con.equals(info.getConnection()))
      { return info; }
    }
    return null;
  }
  public static void commit(Statement stmt)
  {
    ObjectUtils.throwAsError(() -> commit(stmt.getConnection(), 2));
  }
  public static void commit(Connection con)
  {
    commit(con, 2);
  }
  private static void commit(Connection con, int offset)
  {
    DatabaseTransactionInfo commit = null;
    synchronized (connections)
    {
      Iterator<DatabaseTransactionInfo> i = connections.iterator();
      while (i.hasNext())
      {
        DatabaseTransactionInfo held = (DatabaseTransactionInfo) i.next();
        if (held.isFinalizeable())
        {
          i.remove();
        }
        else if (held.isOriginator(con, 1 + offset))
        {
          commit = held;
          i.remove();
        }
      }
    }
    if (commit != null)
    {
      try
      {
        con.commit();
        commit.cleanConnection();
      }
      catch (SQLException e)
      {
        throw ObjectUtils.throwAsError(e);
      }
    }
  }
  public static void rollback(Statement stmt)
  {
    ObjectUtils.throwAsError(() -> rollback(stmt.getConnection()));
  }
  public static void rollback(Connection con)
  {
    if (con != null)
    {
      ObjectUtils.throwAsError(() -> con.rollback());
    }
  }
  public static String formatBoolean(boolean b)
  {
    return b ? "'1'" : "'0'"; // added quotes for postgres
  }
  /**
   * This is for none space single words, such as email, name, handle
   */
  public static boolean isAcceptableDatabaseString(String string)
  {
    if (string == null)
    { return true; }
    for (int i = 0; i < string.length(); i++)
    {
      char c = string.charAt(i);
      boolean bad = false;
      bad = (c == ' ') || (c == '\n') || (c == '\t') || (c == '\'') || (c == '"');
      if (bad)
      { return false; }
    }
    return true;
  }
  public static String getLike(int databaseType)
  {
    return databaseType == POSTGRESQL ? "ILIKE" : "LIKE";
  }
  /**
   * A convenience function to turn the int's into readable text for debuging.
   * 
   * @param status
   *          The status to be translated
   * @return The Text representation of static variable.
   */
  public static final String getDatabaseStatusString(int status)
  {
    String value = "UNKNOWN DATABASE STATUS";
    switch (status)
    {
      case DatabaseUtils.NEW :
        value = "DatabaseUtils.NEW";
        break;
      case DatabaseUtils.OLD :
        value = "DatabaseUtils.OLD";
        break;
      case DatabaseUtils.NEW_MODIFIED :
        value = "DatabaseUtils.NEWMODIFIED";
        break;
      case DatabaseUtils.OLD_MODIFIED :
        value = "DatabaseUtils.OLDMODIFIED";
        break;
    }
    return value;
  }
  public static void saveAll(DatabaseObject[] databaseObjects, Statement stmt)
  {
    try
    {
      for (int i = 0; i < databaseObjects.length; i++)
      {
        databaseObjects[i].save(stmt);
      }
    }
    catch (SQLException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  public static void close(ResultSet rs)
  {
    if (rs != null)
    {
      try
      {
        rs.close();
      }
      catch (SQLException e)
      {
        throw ObjectUtils.throwAsError(e);
      }
    }
  }
  public static void close(Statement stmt)
  {
    if (stmt != null)
    {
      try
      {
        stmt.close();
      }
      catch (SQLException e)
      {
        throw ObjectUtils.throwAsError(e);
      }
    }
  }
  public static void close(Connection con)
  {
    if (con != null)
    {
      try
      {
        con.close();
      }
      catch (SQLException e)
      {
        throw ObjectUtils.throwAsError(e);
      }
    }
  }
}
