package com.spun.util.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyAmountTest
{
  @Test
  public void testWithoutCents() {
    assertEquals("$35", new CurrencyAmount(35.44).withoutCents());
  }
  @Test
  void testCurrencyWithCents()
  {
    assertEquals("$44.36", new CurrencyAmount(44.356).toString());
  }
}
