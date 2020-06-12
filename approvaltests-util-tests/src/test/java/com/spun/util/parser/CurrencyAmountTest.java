package com.spun.util.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CurrencyAmountTest
{
  @Test
  public void testWithoutCents() throws Exception
  {
    assertEquals("$35", new CurrencyAmount(35.44).withoutCents());
  }
}
