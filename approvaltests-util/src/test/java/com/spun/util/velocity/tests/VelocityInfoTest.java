package com.spun.util.velocity.tests;

import junit.framework.TestCase;
import org.apache.velocity.context.Context;
import org.apache.velocity.util.introspection.Info;
import com.spun.util.velocity.ContextAware;
import com.spun.util.velocity.VelocityParser;
import com.spun.util.velocity.VelocityParsingError;

public class VelocityInfoTest extends TestCase implements ContextAware
{
  /***********************************************************************/
  public void testInfoForField() throws Exception
  {
    VelocityParsingError t = getParsingErrorFor("$main.unknownField");
    assertEquals(t.getMessage(), "Did not find com.spun.util.velocity.tests.VelocityInfoTest.unknownField    at [1,7] in template $main.unknownField");
    assertInfoEqual(t.getInfo(), "$main.unknownField", 1, 7);
  }
  /***********************************************************************/
  public void testInfoForMethod()
  {
    VelocityParsingError t = getParsingErrorFor("$main.unknownMethod()");
    assertEquals(t.getMessage().trim(), "Method com.spun.util.velocity.tests.VelocityInfoTest.unknownMethod()  does not exist.   at [1,1] in template");
  }
  /***********************************************************************/
  private void assertInfoEqual(Info i, String name, int line, int column)
  {
    assertEquals("Template Name", name, i.getTemplateName());
    assertEquals("Template Line", line, i.getLine());
    assertEquals("Template Column", column, i.getColumn());
  }
  /***********************************************************************/
  public VelocityParsingError getParsingErrorFor(String velocity)
  {
    try
    {
      VelocityParser.parseString(velocity, this);
      fail("Testable Uberspect Should have thrown an error");
      throw new Error("Shouldn't be able to reach this point");
    }
    catch (VelocityParsingError t)
    {
      return t;
    }
  }
  /***********************************************************************/
  public void setupContext(Context context)
  {
    context.put("main", this);
  }
}
/***********************************************************************/
/***********************************************************************/
