package com.spun.util.database;

import com.spun.util.DatabaseUtils;
import com.spun.util.date.DateRange;

import java.sql.Timestamp;
import java.util.Date;

/*
 * This class is immutable
 */
public class SQLWhere
{
  private String part;
  public SQLWhere(String part)
  {
    this.part = part.trim();
  }

  public SQLWhere(ColumnMetadata metadata, String alias, String comparator, Object part2)
  {
    this(SQLUtils.compareBy(metadata, alias, comparator, part2));
  }

  public SQLWhere(ColumnMetadata metadata, String alias, Object value)
  {
    this(SQLUtils.compareByEquals(metadata, alias, value));
  }

  public SQLWhere(String part1, String comparator, ColumnMetadata metadata, String alias)
  {
    this(part1 + " " + comparator + " " + metadata.getNameWithPrefix(alias));
  }

  public SQLWhere(ColumnMetadata metadata1, String alias1, String comparator, ColumnMetadata metadata2,
      String alias2)
  {
    this(metadata1.getNameWithPrefix(alias1) + " " + comparator + " " + metadata2.getNameWithPrefix(alias2));
  }

  public SQLWhere(String part1, String comparator, String part2)
  {
    this(part1 + " " + comparator + " " + part2);
  }

  public SQLWhere(ColumnMetadata metadata, String alias, boolean value)
  {
    this(SQLUtils.compareBy(metadata, alias, "=", value));
  }

  public SQLWhere(SQLQuery query, String comparator, String value)
  {
    this("(" + query.toString() + ")", comparator, value);
  }

  public static SQLWhere joinByOr(SQLWhere a, SQLWhere b)
  {
    if (a == null)
    {
      return b;
    }
    else if (b == null)
    { return a; }
    return new SQLWhere(join(a, "OR", b));
  }

  public static SQLWhere joinByOr(SQLWhere a, SQLWhere b, SQLWhere c)
  {
    return new SQLWhere(a.toString() + " OR " + b.toString() + " OR " + c.toString());
  }

  private static String join(SQLWhere a, String join, SQLWhere b)
  {
    return a.toString() + " " + join + " " + b.toString();
  }

  public SQLWhere joinByAnd(SQLWhere a)
  {
    return joinByAnd(this, a);
  }

  public static SQLWhere joinByAnd(SQLWhere a, SQLWhere b)
  {
    if (a == null)
    {
      return b;
    }
    else if (b == null)
    { return a; }
    return new SQLWhere(join(a, "AND", b));
  }

  @Override
  public String toString()
  {
    return isPartWrapped(part) ? part : "(" + part + ")";
  }

  public static boolean isPartWrapped(String part)
  {
    if (part.charAt(0) != '(' || part.charAt(part.length() - 1) != ')')
    { return false; }
    int count = 1;
    for (int i = 1; i < part.length() - 1; i++)
    {
      switch (part.charAt(i))
      {
        case '(' :
          count++;
          break;
        case ')' :
          count--;
          if (count == 0)
          { return false; }
          break;
      }
    }
    return true;
  }

  public static SQLWhere createBetween(ColumnMetadata column, String prefix, DateRange range)
  {
    return createBetween(range.getStart(), column, prefix, range.getEnd());
  }

  public static SQLWhere createBetween(Date startTime, ColumnMetadata column, String prefix, Date endTime)
  {
    return new SQLWhere(SQLUtils.createSQLBetween(new Timestamp(startTime.getTime()).toString(),
        column.getNameWithPrefix(prefix), new Timestamp(endTime.getTime()).toString()));
  }

  public static SQLWhere createInSQLStatement(ColumnMetadata metadata, String alias, Object... values)
  {
    return new SQLWhere(SQLUtils.createInSQLStatement(metadata, alias, false, values));
  }

  public static SQLWhere createNotInSQLStatement(ColumnMetadata metadata, String alias, Object[] values)
  {
    return new SQLWhere(SQLUtils.createInSQLStatement(metadata, alias, true, values));
  }

  public static SQLWhere createInSQLStatement(ColumnMetadata metadata, String alias, boolean not, Object[] values)
  {
    return new SQLWhere(SQLUtils.createInSQLStatement(metadata, alias, not, values));
  }

  public static SQLWhere createLike(ColumnMetadata metaData, String alias, String search, int databaseType)
  {
    SQLWhere sql = new SQLWhere(metaData, alias, DatabaseUtils.getLike(databaseType), search);
    return sql;
  }

  public static SQLWhere createNotNull(ColumnMetadata metadata, String alias)
  {
    return new SQLWhere(SQLUtils.compareBy(metadata, alias, "!=", null));
  }

  public static SQLWhere createNotLike(ColumnMetadata metaData, String alias, String search, int databaseType)
  {
    SQLWhere sql = new SQLWhere(metaData, alias, "NOT " + DatabaseUtils.getLike(databaseType), search);
    return sql;
  }
}
