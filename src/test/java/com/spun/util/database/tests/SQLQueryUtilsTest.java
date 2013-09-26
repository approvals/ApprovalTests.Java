package com.spun.util.database.tests;

import junit.framework.TestCase;

import com.spun.util.database.SQLQuery;
import com.spun.util.database.SQLQueryUtils;

public class SQLQueryUtilsTest
  extends TestCase 
{

  /***********************************************************************/
  public void testCountingQuery() 
  {
    SQLQuery query = new SQLQuery();
    query.addSelect("a.*");
    query.addSelect("b.*");
    query.addSelect("c.*");
    query.addSelect("d.*");
    query.addFrom("company_order");
    query.addFromWithInnerJoin("company", "a.company", "pkey");
    query.addFromWithInnerJoin("company_address", "a.bill_to", "pkey");
    query.addFromWithInnerJoin("company_address", "a.ship_to", "pkey");
    query.addWhere("b.category = 'Store'", true);
    query.addOrderBy("a.pkey ", true);
    query.addLimit(6, 5, "a", "pkey");
    SQLQueryUtils.refineLimitQuery(10, query);
    query.toString();
    assertTrue("reversed", query.isOrderReversed());
    assertEquals("amount of rows adjusted", 4, query.getLimitPart().numberOfRowsDesired);
    assertEquals("getTableForAliasWorks", "a", query.getFirstAliasForTableName("company_order"));
  }
  
  /***********************************************************************/
  /***********************************************************************/
}

