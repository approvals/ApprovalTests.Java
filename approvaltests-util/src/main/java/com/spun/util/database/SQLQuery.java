package com.spun.util.database;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.spun.util.DatabaseUtils;
import com.spun.util.ObjectUtils;
import com.spun.util.StringUtils;

public class SQLQuery
{
  public static final class JOINS
  {
    public static final String INNER_JOIN       = "INNER JOIN";
    public static final String LEFT_OUTER_JOIN  = "LEFT OUTER JOIN";
    public static final String RIGHT_OUTER_JOIN = "RIGHT OUTER JOIN";
  }
  public static final String     BREAK    = "\n";
  private ArrayList<String>      select   = null;
  private ArrayList<FromPart>    from     = null;
  private SQLWhere               where    = null;
  private ArrayList<OrderByPart> orderBy  = null;
  private ArrayList<String>      groupBy  = null;
  private ArrayList<String>      having   = null;
  private boolean                reversed = false;
  private LimitPart              limitPart;
  private int                    tableAliasOffset;
  private boolean                distinct;
  public SQLQuery()
  {
    this(0);
  }
  public SQLQuery(int tableAliasOffset)
  {
    this.tableAliasOffset = tableAliasOffset;
    select = new ArrayList<String>();
    from = new ArrayList<FromPart>();
    where = null;
    orderBy = new ArrayList<OrderByPart>();
    groupBy = new ArrayList<String>();
    having = new ArrayList<String>();
  }
  public void addSelect(String part)
  {
    select.add(part);
  }
  public void addSelect(String part, String alias)
  {
    select.add(part + " AS " + alias);
  }
  public void addDistinct()
  {
    distinct = true;
  }
  public boolean isDistinct()
  {
    return distinct;
  }
  public String getFirstAliasForTableName(String tableName)
  {
    for (int i = 0; i < from.size(); i++)
    {
      FromPart part = (FromPart) from.get(i);
      if (part.part.indexOf(tableName + " AS") != -1)
      { return "" + ((char) ('a' + i)); }
    }
    return null;
  }
  public void addFromPart(FromPart from)
  {
    this.from.add(from);
  }
  public void addOrderByPart(OrderByPart orderBy)
  {
    this.orderBy.add(orderBy);
  }
  public void setLimitPart(LimitPart limit)
  {
    this.limitPart = limit;
  }
  public int getAliasCount()
  {
    return getFromParts().length;
  }
  public String addFrom(String table)
  {
    String alias = "" + (char) ('a' + tableAliasOffset + from.size());
    from.add(new FromPart(table + " AS " + alias, false));
    return alias;
  }
  public String addFromWithInnerJoin(String table, String joinWith, String joinOn)
  {
    return addFromWithJoin(table, joinWith, joinOn, JOINS.INNER_JOIN);
  }
  public String addFromWithLeftOuterJoin(String table, String joinWith, String joinOn)
  {
    return addFromWithJoin(table, joinWith, joinOn, JOINS.LEFT_OUTER_JOIN);
  }
  public String addFromWithRightOuterJoin(String table, String joinWith, String joinOn)
  {
    return addFromWithJoin(table, joinWith, joinOn, JOINS.RIGHT_OUTER_JOIN);
  }
  public String addFromWithJoin(String table, String joinWith, String joinOn, String joinType)
  {
    String alias = "" + (char) ('a' + tableAliasOffset + from.size());
    String sql = (joinType + " " + table + " AS " + alias + " ON " + joinWith + " = " + alias + "." + joinOn);
    from.add(new FromPart(sql, true));
    return alias;
  }
  public void addWhere(String part)
  {
    addWhere(new SQLWhere(part), true);
  }
  public void addWhere(SQLWhere part)
  {
    addWhere(part, true);
  }
  public void addWhere(String part, boolean joinWithAnd)
  {
    addWhere(new SQLWhere(part), joinWithAnd);
  }
  public void addWhere(SQLWhere part, boolean joinWithAnd)
  {
    where = joinWithAnd ? SQLWhere.joinByAnd(where, part) : SQLWhere.joinByOr(where, part);
  }
  public String toString()
  {
    return toString(DatabaseUtils.SQLSERVER);
  }
  public String toString(Statement stmt)
  {
    try
    {
      return toString(DatabaseUtils.getDatabaseType(stmt));
    }
    catch (SQLException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  public String toString(int databaseType)
  {
    SQLQueryWriter writer = getSQLQueryWriter(databaseType);
    return writer.toString(this);
  }
  private SQLQueryWriter getSQLQueryWriter(int databaseType)
  {
    if (limitPart == null || DatabaseUtils.MY_SQL == databaseType)
    {
      return new SimpleQueryWriter(databaseType);
    }
    else if (limitPart.startingZeroBasedIndex == 0)
    {
      return new SimpleQueryWriter(databaseType);
    }
    else
    {
      return new ReverseOrderLimitQueryWriter(databaseType);
    }
  }
  public void addOrderBy(String orderByClause, boolean ascending)
  {
    orderBy.add(new OrderByPart(orderByClause, ascending));
  }
  public void addOrderBy(ColumnMetadata submitted, String alias, boolean ascending)
  {
    addOrderBy(submitted.getNameWithPrefix(alias), ascending);
  }
  public void addGroupBy(String groupByClause)
  {
    groupBy.add(groupByClause);
  }
  public void addHaving(String havingClause)
  {
    having.add(havingClause);
  }
  public void setOrderReversed(boolean reversed)
  {
    this.reversed = reversed;
  }
  public boolean isOrderReversed()
  {
    return reversed;
  }
  public void addLimit(int startingZeroBasedIndex, int numberOfRowsDesired, String mainTableAlias,
      String mainTablePkeyColumn)
  {
    this.limitPart = new LimitPart(startingZeroBasedIndex, numberOfRowsDesired, mainTableAlias,
        mainTablePkeyColumn);
  }
  public void addLimit(int startingZeroBasedIndex, int numberOfRowsDesired, String mainTableAlias,
      ColumnMetadata mainTablePkeyColumn)
  {
    addLimit(startingZeroBasedIndex, numberOfRowsDesired, mainTableAlias, mainTablePkeyColumn.getName());
  }
  public LimitPart getLimitPart()
  {
    return limitPart;
  }
  public String[] getSelectParts()
  {
    return StringUtils.toArray(select);
  }
  public String[] getGroupByParts()
  {
    return StringUtils.toArray(groupBy);
  }
  public String[] getHavingParts()
  {
    return StringUtils.toArray(having);
  }
  public FromPart[] getFromParts()
  {
    return (FromPart[]) from.toArray(new FromPart[from.size()]);
  }
  public SQLWhere getWherePart()
  {
    return where;
  }
  public OrderByPart[] getOrderByParts()
  {
    return (OrderByPart[]) orderBy.toArray(new OrderByPart[orderBy.size()]);
  }
  /**                       INNER CLASSES                                  **/
  public static class LimitPart implements Cloneable
  {
    private int   startingZeroBasedIndex;
    public int    numberOfRowsDesired;
    public String mainTableAlias;
    public String mainTablePkeyColumn;
    public LimitPart(int startingZeroBasedIndex, int numberOfRowsDesired, String mainTableAlias,
        String mainTablePkeyColumn)
    {
      this.setStartingZeroBasedIndex(startingZeroBasedIndex);
      this.numberOfRowsDesired = numberOfRowsDesired;
      this.mainTableAlias = mainTableAlias;
      this.mainTablePkeyColumn = mainTablePkeyColumn;
    }
    public int getStartingZeroBasedIndex()
    {
      return startingZeroBasedIndex;
    }
    public void setStartingZeroBasedIndex(int startingZeroBasedIndex)
    {
      if (startingZeroBasedIndex < 0)
      { throw new Error("startingZeroBasedIndex: " + startingZeroBasedIndex + " must be greater than 0."); }
      this.startingZeroBasedIndex = startingZeroBasedIndex;
    }
  }
  public static class OrderByPart implements Cloneable
  {
    public String  part      = null;
    public boolean ascending = false;
    public OrderByPart(String part, boolean ascending)
    {
      this.part = part;
      this.ascending = ascending;
    }
    public String toString(boolean isFirst)
    {
      String sql = part + (ascending ? " ASC " : " DESC ");
      if (!isFirst)
      {
        sql = (isFirst ? " " : ", ") + sql;
      }
      return sql;
    }
  }
  public static class FromPart implements Cloneable
  {
    public String  part   = null;
    public boolean isJoin = false;
    public FromPart(String part, boolean isJoin)
    {
      this.part = part;
      this.isJoin = isJoin;
    }
    public String toString(boolean isFirst)
    {
      String sql = part;
      if (!isFirst)
      {
        sql = (isJoin ? " " : ", ") + sql;
      }
      return sql;
    }
  }
}
