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
    Info i = getInfoFor("$main.unknownField");
    assertInfoEqual(i, "$main.unknownField", 1, 7);
  }
  /***********************************************************************/
  public void testInfoForMethod()
  {
    Info i = getInfoFor("$main.unknownMethod()");
    assertInfoEqual(i, "$main.unknownMethod()", 1, 7);
  }
  /***********************************************************************/
  private void assertInfoEqual(Info i, String name, int line, int column)
  {
    assertEquals("Template Name", name, i.getTemplateName());
    assertEquals("Template Line", line, i.getLine());
    assertEquals("Template Column", column, i.getColumn());
  }
  /***********************************************************************/
  public Info getInfoFor(String velocity)
  {
    try
    {
      VelocityParser.parseString(velocity, this);
      fail("Testable Uberspect Should have thrown an error");
      throw new Error("Shouldn't be able to reach this point");
    }
    catch (VelocityParsingError t)
    {
      return t.getInfo();
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
