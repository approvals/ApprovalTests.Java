package com.spun.util.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SQLQueryUtilsTest
{
  @Test
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
    assertTrue(query.isOrderReversed(), "reversed");
    assertEquals(4, query.getLimitPart().numberOfRowsDesired, "amount of rows adjusted");
    assertEquals("a", query.getFirstAliasForTableName("company_order"), "getTableForAliasWorks");
  }
}
