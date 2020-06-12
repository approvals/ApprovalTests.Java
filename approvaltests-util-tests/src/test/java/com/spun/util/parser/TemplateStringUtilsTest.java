package com.spun.util.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TemplateStringUtilsTest
{
  @Test
  public void testTextPadding()
  {
    assertEquals("Hello Jack          ", TemplateStringUtils.pad("Hello Jack", 20, " "));
  }
}
