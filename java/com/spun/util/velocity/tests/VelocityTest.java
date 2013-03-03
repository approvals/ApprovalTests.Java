package com.spun.util.velocity.tests;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.velocity.context.Context;
import com.spun.util.velocity.ContextAware;
import com.spun.util.velocity.VelocityParser;

public class VelocityTest extends TestCase implements ContextAware
{
  /**********************77*************************************************/
  public void testCodeWorks() throws Exception
  {
    assertEquals(getClass().getName(), VelocityParser.parseString("$main.getClass().getName()", this));
  }
  public void testUnknownField()
  {
    assertErrorThrown("$main.unknownField");
  }
  /***********************************************************************/
  public void testUnknownFieldThenMethod()
  {
    assertErrorThrown("$main.unknownField.someMethod()");
  }
  /***********************************************************************/
  private void assertErrorThrown(String string)
  {
    String result = null;
    try
    {
      result = VelocityParser.parseString(string, this);
    }
    catch (Throwable t)
    {
      return;
    }
    Assert.fail("parsing '" + string + "' did not fail but returned '" + result + "'");
  }
  /***********************************************************************/
  public void testUnknownMethod()
  {
    assertErrorThrown("$main.unknownMethod()");
  }
  /***********************************************************************/
  public void testNullPointer()
  {
    assertErrorThrown("$main.getNull().callMethod()");
  }
  /***********************************************************************/
  public Object getNull()
  {
    return null;
  }
  /***********************************************************************/
  public void setupContext(Context context)
  {
    context.put("main", this);
  }
}
/***********************************************************************/
/***********************************************************************/
