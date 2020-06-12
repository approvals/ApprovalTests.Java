package com.spun.util.velocity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.velocity.context.Context;
import org.apache.velocity.util.introspection.Info;
import org.junit.jupiter.api.Test;

public class VelocityInfoTest implements ContextAware
{
  @Test
  public void testInfoForField() throws Exception
  {
    VelocityParsingError t = getParsingErrorFor("$main.unknownField");
    assertEquals(
        "Did not find com.spun.util.velocity.VelocityInfoTest.unknownField    at [1,7] in template $main.unknownField",
        t.getMessage());
    assertInfoEqual(t.getInfo(), "$main.unknownField", 1, 7);
  }
  @Test
  public void testInfoForMethod()
  {
    VelocityParsingError t = getParsingErrorFor("$main.unknownMethod()");
    assertEquals(
        "Method com.spun.util.velocity.VelocityInfoTest.unknownMethod()  does not exist.   at [1,1] in template",
        t.getMessage().trim());
  }
  private void assertInfoEqual(Info i, String name, int line, int column)
  {
    assertEquals(name, i.getTemplateName(), "Template Name");
    assertEquals(line, i.getLine(), "Template Line");
    assertEquals(column, i.getColumn(), "Template Column");
  }
  public VelocityParsingError getParsingErrorFor(String velocity)
  {
    try
    {
      VelocityParser.parseString(velocity, this);
      return null;
    }
    catch (VelocityParsingError t)
    {
      return t;
    }
  }
  @Override
  public void setupContext(Context context)
  {
    context.put("main", this);
  }
}
