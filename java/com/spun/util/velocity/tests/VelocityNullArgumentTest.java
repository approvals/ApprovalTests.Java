package com.spun.util.velocity.tests;

import junit.framework.TestCase;
import org.apache.velocity.context.Context;
import com.spun.util.velocity.ContextAware;

public class VelocityNullArgumentTest extends TestCase implements ContextAware
{
  /***********************************************************************/
  public void testOverloadedMethodFound() throws Exception
  {
   // assertEquals("you got null", VelocityParser.parseString("$object.getClass($nullValue)", this));
  }
  /***********************************************************************/
  public void setupContext(Context context)
  {
    context.put("object", this);
    context.put("nullValue", null);
  }
  /***********************************************************************/
  public static String getClass(Class<?> c)
  {
    return c == null ? "you got null" : c.getName();
  }
  /***********************************************************************/
  public static String getClass(String c)
  {
    return c == null ? "you got null" : c.getClass().getName();
  }
  /***********************************************************************/
  /***********************************************************************/
}
