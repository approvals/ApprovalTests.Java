package com.spun.util.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemplateStringUtilsTest
{
  @Test
  public void testTextPadding()
  {
    assertEquals("Hello Jack          ", TemplateStringUtils.pad("Hello Jack", 20, " "));
  }
}
