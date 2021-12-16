/*
 * Created on Mar 25, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.spun.util.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import com.spun.util.ObjectUtils;
import com.spun.util.database.SQLQuery.FromPart;
import com.spun.util.database.SQLQuery.LimitPart;

public class SQLQueryUtils
{
  public static class IntegerExtractor implements ResultSetExtractor<Integer>
  {
    @Override
    public Integer extract(ResultSet rs)
    {
      return ObjectUtils.throwAsError(() -> rs.getInt(1));
    }
  }
  public static class TimestampExtractor implements ResultSetExtractor<Timestamp>
  {
    @Override
    public Timestamp extract(ResultSet rs)
    {
      return ObjectUtils.throwAsError(() -> rs.getTimestamp(1));
    }
  }
  public static interface ResultSetExtractor<T>
  {
    public T extract(ResultSet rs);
  }
  public static SQLQuery extractCountingQuery(SQLQuery query)
  {
    SQLQuery counter = new SQLQuery();
    // add the from parts
    FromPart[] fromParts = query.getFromParts();
    for (int i = 0; i < fromParts.length; i++)
    {
      counter.addFromPart(fromParts[i]);
    }
    SQLWhere where = query.getWherePart();
    counter.addWhere(where);
    counter.addSelect("count(*) AS thecount ");
    return counter;
  }
  public static SQLQuery refineLimitQuery(int maximum, SQLQuery query)
  {
    LimitPart limitPart = query.getLimitPart();
    if (limitPart.getStartingZeroBasedIndex() >= maximum)
    {
      limitPart.setStartingZeroBasedIndex(maximum - 1);
    }
    if (limitPart.numberOfRowsDesired + limitPart.getStartingZeroBasedIndex() > maximum)
    {
      limitPart.numberOfRowsDesired = maximum - limitPart.getStartingZeroBasedIndex();
    }
    return query;
  }
  public static int executeCountOnQuery(SQLQuery query, Statement stmt)
  {
    return executeSingleIntQuery(extractCountingQuery(query).toString(), stmt);
  }
  public static int executeSingleIntQuery(String sql, Statement stmt)
  {
    ResultSet rs = SQLStatementUtils.executeQuery(sql, stmt);
    return extractSingleRow(sql, rs, new IntegerExtractor());
  }
  public static Timestamp executeSingleDateQuery(String sql, Statement stmt)
  {
    ResultSet rs = SQLStatementUtils.executeQuery(sql, stmt);
    return extractSingleRow(sql, rs, new TimestampExtractor());
  }
  private static <T> T extractSingleRow(String sql, ResultSet rs, ResultSetExtractor<T> extractor)
  {
    try
    {
      if (rs.next())
      {
        T out = extractor.extract(rs);
        rs.close();
        return out;
      }
      else
      {
        throw new RuntimeException("No results returned from query - " + sql);
      }
    }
    catch (SQLException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
}
