package com.spun.util.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TemplateDoubleTest
{
  @Test
  public void testPercentageAmount()
  {
    assertEquals("10.46%", new PercentageAmount(.10457).toString());
  }
}
