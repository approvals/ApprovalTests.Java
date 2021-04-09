/*
 * Created on Mar 25, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.spun.util.database;

import com.spun.util.database.SQLQuery.FromPart;
import com.spun.util.database.SQLQuery.LimitPart;
import com.spun.util.database.SQLQuery.OrderByPart;
import com.spun.util.servlets.ValidationError;

/**
 * @author Llewellyn Falco
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ReverseOrderLimitQueryWriter implements SQLQueryWriter
{
  private int databaseType;
  public ReverseOrderLimitQueryWriter(int databaseType)
  {
    this.databaseType = databaseType;
  }
  public String toString(SQLQuery query)
  {
    assertQueryValid(query);
    query.setOrderReversed(true);
    String innerQuery = constructInnerQuery(query).toString(databaseType);
    String outerQuery = constructOuterQuery(innerQuery, query).toString(databaseType);
    return outerQuery;
  }
  private SQLQuery constructOuterQuery(String innerQuery, SQLQuery query)
  {
    SQLQuery outer = new SQLQuery(query.getAliasCount());
    // add the from parts
    Replacer replacer = new Replacer(query.getFromParts().length);
    FromPart[] fromParts = query.getFromParts();
    for (int i = 0; i < fromParts.length; i++)
    {
      outer.addFromPart(new FromPart(replacer.replace(fromParts[i].part), fromParts[i].isJoin));
    }
    String alias = outer.addFrom("(" + innerQuery + ")");
    String pkeyColumn = "." + query.getLimitPart().mainTablePkeyColumn;
    outer.addWhere(alias + pkeyColumn + " = " + replacer.replace(query.getLimitPart().mainTableAlias) + pkeyColumn,
        true);
    OrderByPart[] orderByParts = query.getOrderByParts();
    for (int i = 0; i < orderByParts.length; i++)
    {
      outer.addOrderBy(replacer.replace(orderByParts[i].part), !orderByParts[i].ascending);
    }
    String[] selects = query.getSelectParts();
    for (int i = 0; i < selects.length; i++)
    {
      outer.addSelect(replacer.replace(selects[i]));
    }
    outer.setLimitPart(new LimitPart(0, query.getLimitPart().numberOfRowsDesired,
        query.getLimitPart().mainTableAlias, query.getLimitPart().mainTablePkeyColumn));
    return outer;
  }
  private SQLQuery constructInnerQuery(SQLQuery query)
  {
    SQLQuery inner = new SQLQuery();
    // add the from parts
    FromPart[] fromParts = query.getFromParts();
    for (int i = 0; i < fromParts.length; i++)
    {
      inner.addFromPart(fromParts[i]);
    }
    SQLWhere wherePart = query.getWherePart();
    inner.addWhere(wherePart);
    OrderByPart[] orderByParts = query.getOrderByParts();
    for (int i = 0; i < orderByParts.length; i++)
    {
      inner.addOrderByPart(orderByParts[i]);
    }
    inner.addSelect(query.getLimitPart().mainTableAlias + "." + query.getLimitPart().mainTablePkeyColumn + " AS "
        + query.getLimitPart().mainTablePkeyColumn);
    inner.setLimitPart(new LimitPart(0,
        query.getLimitPart().numberOfRowsDesired + query.getLimitPart().getStartingZeroBasedIndex(),
        query.getLimitPart().mainTableAlias, query.getLimitPart().mainTablePkeyColumn));
    return inner;
  }
  private void assertQueryValid(SQLQuery query)
  {
    String[] assertions = new String[]{"groupByNull", "limitExists", "mainTableValid", "mainTablePkeyValid"};
    ValidationError error = new ValidationError(assertions);
    error.setError("groupByNull", query.getGroupByParts().length > 0, "Cannot have group by in limit query.");
    error.setError("limitExists",
        query.getLimitPart() == null || query.getLimitPart().getStartingZeroBasedIndex() == 0,
        "Must have a lower limit.");
    error.setError("mainTableValid", query.getLimitPart() == null || query.getLimitPart().mainTableAlias == null,
        "Must define main table.");
    error.setError("mainTablePkeyValid",
        query.getLimitPart() == null || query.getLimitPart().mainTablePkeyColumn == null,
        "Must define main table pkey column name.");
    if (!error.isOk())
    { throw error; }
  }
  public static class Replacer
  {
    int index;
    public Replacer(int index)
    {
      this.index = index;
    }
    public String replace(String string)
    {
      for (int i = 0; i < index; i++)
      {
        char letter = (char) ('a' + i);
        string = string.replaceAll("\\b" + letter + "\\b", letter + "1");
      }
      return string;
    }
  }
}
