package com.spun.util.parser.tests;

import com.spun.util.parser.CurrencyAmount;

import junit.framework.TestCase;

public class CurrencyAmountTest extends TestCase
{
  public void testWithoutCents() throws Exception
  {
    assertEquals("$35", new CurrencyAmount(35.44).withoutCents());
  }
}
