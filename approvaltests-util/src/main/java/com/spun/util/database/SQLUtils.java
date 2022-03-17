package com.spun.util.database;

import com.spun.util.DatabaseUtils;
import com.spun.util.ObjectUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUtils
{
  public static String createInSQLStatement(DatabaseObject[] from)
  {
    from = (from == null) ? new DatabaseObject[0] : from;
    Integer[] array = new Integer[from.length];
    for (int i = 0; i < from.length; i++)
    {
      array[i] = from[i].getPkey();
    }
    return createInSQLStatement((Object[]) array);
  }
  public static String createInSQLStatement(String[] values)
  {
    return createInSQLStatement((Object[]) values);
  }
  public static String createInSQLStatement(Object[] values)
  {
    if (values == null || values.length == 0)
    { return "(null)"; }
    StringBuffer sql = new StringBuffer("(");
    for (int i = 0; i < values.length; i++)
    {
      sql.append(DatabaseUtils.formatNullableObject(values[i]));
      sql.append(", ");
    }
    sql.setLength(sql.length() - 2);
    sql.append(") ");
    return sql.toString();
  }
  public static String loadInSQLStatement(ResultSet rs)
  {
    StringBuffer sql = new StringBuffer("(");
    try
    {
      while (rs.next())
      {
        sql.append(DatabaseUtils.formatNullableObject(rs.getObject(1)));
        sql.append(", ");
      }
      if (sql.length() == 1)
      { return null; }
      sql.setLength(sql.length() - 2);
      sql.append(") ");
      return sql.toString();
    }
    catch (SQLException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  public static String createSQLBetween(String lowerValue, String betweenVariable, String upperValue)
  {
    return "(" + DatabaseUtils.formatNullableObject(lowerValue) + " <= " + betweenVariable + " AND "
        + betweenVariable + " < " + DatabaseUtils.formatNullableObject(upperValue) + ")";
  }
  public static String compareBy(ColumnMetadata metadata, String alias, String compareBy, Object value)
  {
    String sql = null;
    if (value == null && "=".equals(compareBy))
    {
      sql = metadata.getNameWithPrefix(alias) + " IS NULL";
    }
    else if (value == null && "!=".equals(compareBy))
    {
      sql = metadata.getNameWithPrefix(alias) + " IS NOT NULL";
    }
    else
    {
      sql = metadata.getNameWithPrefix(alias) + " " + compareBy + " " + DatabaseUtils.formatNullableObject(value);
    }
    return sql;
  }
  public static String compareBy(ColumnMetadata metadata, String alias, String compareBy, boolean value)
  {
    return metadata.getNameWithPrefix(alias) + " " + compareBy + " " + DatabaseUtils.formatBoolean(value);
  }
  public static String compareByEquals(ColumnMetadata metadata, String alias, Object value)
  {
    return compareBy(metadata, alias, "=", value);
  }
  public static String createInSQLStatement(ColumnMetadata metadata, String alias, Object[] values)
  {
    return createInSQLStatement(metadata, alias, false, values);
  }
  public static String createInSQLStatement(ColumnMetadata metadata, String alias, boolean not, Object[] values)
  {
    String sql = null;
    String in = not ? " NOT IN " : " IN ";
    if (values == null)
    {
      sql = compareBy(metadata, alias, not ? "!=" : "=", null);
    }
    else if (values.length == 1)
    {
      sql = compareBy(metadata, alias, not ? "!=" : "=", values[0]);
    }
    else if (values instanceof DatabaseObject[])
    {
      sql = metadata.getNameWithPrefix(alias) + in + createInSQLStatement((DatabaseObject[]) values);
    }
    else
    {
      sql = metadata.getNameWithPrefix(alias) + in + createInSQLStatement(values);
    }
    return sql;
  }
}
