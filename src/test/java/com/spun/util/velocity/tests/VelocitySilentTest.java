package com.spun.util.velocity.tests;

import junit.framework.TestCase;
import org.apache.velocity.context.Context;
import com.spun.util.velocity.ContextAware;
import com.spun.util.velocity.VelocityParser;

public class VelocitySilentTest
  extends TestCase implements ContextAware 
{
  /***********************************************************************/
  public void testMethod()  throws Exception
  {
    assertEquals("", VelocityParser.parseString("$!main.toString()", this));
  }
  /***********************************************************************/
  public void testField()  throws Exception
  {
    // using the toString implied method
    assertEquals("", VelocityParser.parseString("$!main", this));
  }
  /***********************************************************************/
  public Object getInstance()
  {
    return this;
  }
  /***********************************************************************/
  public String toString()
  {
    return null;
  }
  /***********************************************************************/
  public void setupContext(Context context)
  {
    context.put("main", this);
    context.put("nullValue", null);
  }
	
}
/***********************************************************************/
/***********************************************************************/
