package com.spun.util.velocity;

import org.apache.velocity.context.Context;
import org.junit.Assert;

import com.spun.util.velocity.ContextAware;
import com.spun.util.velocity.VelocityParser;

import junit.framework.TestCase;

public class VelocityTest extends TestCase implements ContextAware
{

  public void testCodeWorks() throws Exception
  {
    assertEquals(getClass().getName(), VelocityParser.parseString("$main.getClass().getName()", this));
  }
  public void testUnknownField()
  {
    assertErrorThrown("$main.unknownField");
  }
  
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
    Assert.fail("parsing '" + string + "' did not fail but returned '" + result + "'");
  }
  
  public void testUnknownMethod()
  {
    assertErrorThrown("$main.unknownMethod()");
  }
  
  /*public void testNullPointer()
  {
    assertErrorThrown("$main.getNull().callMethod()");
  }*/
  
  public Object getNull()
  {
    return null;
  }
  
  public void setupContext(Context context)
  {
    context.put("main", this);
  }
}


