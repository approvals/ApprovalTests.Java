package com.spun.util.velocity;

import org.apache.velocity.context.Context;
import org.apache.velocity.util.introspection.Info;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    String template = "$main.unknownMethod()";
    VelocityParsingError t = getParsingErrorFor(template);
    Approvals.verify(String.format("Template: %s \n generated error:\n %s", template, t.getMessage().trim()));
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
