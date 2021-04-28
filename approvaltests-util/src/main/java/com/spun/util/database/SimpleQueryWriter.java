package com.spun.util.database;

import com.spun.util.DatabaseUtils;
import com.spun.util.database.SQLQuery.FromPart;
import com.spun.util.database.SQLQuery.OrderByPart;

public class SimpleQueryWriter implements SQLQueryWriter
{
  private int databaseType;
  public SimpleQueryWriter(int databaseType)
  {
    this.databaseType = databaseType;
  }
  public String toString(SQLQuery query)
  {
    StringBuffer sql = new StringBuffer("SELECT ");
    if (query.isDistinct())
    {
      sql.append("DISTINCT ");
    }
    // limit
    if (DatabaseUtils.isSqlServer(databaseType) && query.getLimitPart() != null)
    {
      if (query.getLimitPart().getStartingZeroBasedIndex() != 0)
      {
        throw new Error("Wrong Sql writer for lower bound of " + query.getLimitPart().getStartingZeroBasedIndex());
      }
      sql.append("TOP " + query.getLimitPart().numberOfRowsDesired + " ");
    }
    addSelectPart(sql, query.getSelectParts());
    addFromPart(sql, query.getFromParts());
    addWherePart(sql, query.getWherePart());
    addGroupBy(sql, query.getGroupByParts());
    addHaving(sql, query.getHavingParts());
    addOrderByPart(sql, query.getOrderByParts());
    //limit
    if ((databaseType == DatabaseUtils.POSTGRESQL || databaseType == DatabaseUtils.MY_SQL)
        && query.getLimitPart() != null)
    {
      sql.append(" LIMIT ");
      if (databaseType == DatabaseUtils.MY_SQL && query.getLimitPart().getStartingZeroBasedIndex() != 0)
      {
        sql.append(query.getLimitPart().getStartingZeroBasedIndex() + ", ");
      }
      sql.append(query.getLimitPart().numberOfRowsDesired);
    }
    return sql.toString();
  }
  private void addOrderByPart(StringBuffer sql, OrderByPart[] orderBys)
  {
    //order by 
    if (orderBys.length > 0)
    {
      sql.append(SQLQuery.BREAK + "ORDER BY ");
      for (int i = 0; i < orderBys.length; i++)
      {
        sql.append(orderBys[i].toString(i == 0));
      }
      sql.append(" ");
    }
  }
  private void addWherePart(StringBuffer sql, SQLWhere wherePart)
  {
    if (wherePart != null)
    {
      sql.append(SQLQuery.BREAK + "WHERE ");
      sql.append(wherePart.toString());
    }
  }
  private void addFromPart(StringBuffer sql, FromPart[] froms)
  {
    if (froms.length > 0)
    {
      sql.append(SQLQuery.BREAK + "FROM ");
      for (int i = 0; i < froms.length; i++)
      {
        sql.append(froms[i].toString(i == 0));
      }
      sql.append(" ");
    }
  }
  private void addGroupBy(StringBuffer sql, String[] groupBys)
  {
    if (groupBys.length > 0)
    {
      sql.append(SQLQuery.BREAK + "GROUP BY ");
      for (int i = 0; i < groupBys.length; i++)
      {
        sql.append(groupBys[i]);
        sql.append(", ");
      }
      sql.setLength(sql.length() - 2);
      sql.append(" ");
    }
  }
  private void addHaving(StringBuffer sql, String[] havings)
  {
    if (havings.length > 0)
    {
      sql.append(SQLQuery.BREAK + "HAVING ");
      for (int i = 0; i < havings.length; i++)
      {
        sql.append(havings[i]);
        sql.append(", ");
      }
      sql.setLength(sql.length() - 2);
      sql.append(" ");
    }
  }
  private void addSelectPart(StringBuffer sql, String[] selectParts)
  {
    for (int i = 0; i < selectParts.length; i++)
    {
      sql.append(selectParts[i]);
      sql.append(", ");
    }
    sql.setLength(sql.length() - 2);
    sql.append(" ");
  }
}
