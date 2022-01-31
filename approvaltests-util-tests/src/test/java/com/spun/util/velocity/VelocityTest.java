package com.spun.util.velocity;

import org.apache.velocity.context.Context;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class VelocityTest implements ContextAware
{
  @Test
  public void testCodeWorks() throws Exception
  {
    assertEquals(getClass().getName(), VelocityParser.parseString("$main.getClass().getName()", this));
  }
  @Test
  public void testUnknownField()
  {
    assertErrorThrown("$main.unknownField");
  }
  @Test
  public void testUnknownFieldThenMethod()
  {
    assertErrorThrown("$main.unknownField.someMethod()");
  }
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
    fail("parsing '" + string + "' did not fail but returned '" + result + "'");
  }
  @Test
  public void testUnknownMethod()
  {
    assertErrorThrown("$main.unknownMethod()");
  }
  @Disabled("believe velocity needs to be configured to throw nulls")
  @Test
  public void testNullPointer()
  {
    assertErrorThrown("$main.getNull().callMethod()");
  }
  public Object getNull()
  {
    return null;
  }
  @Override
  public void setupContext(Context context)
  {
    context.put("main", this);
  }
}
