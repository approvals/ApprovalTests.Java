package com.spun.util.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyAmountTest
{
  @Test
  public void testWithoutCents() throws Exception
  {
    assertEquals("$35", new CurrencyAmount(35.44).withoutCents());
  }
}
