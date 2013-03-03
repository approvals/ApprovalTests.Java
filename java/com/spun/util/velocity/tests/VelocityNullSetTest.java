package com.spun.util.velocity.tests;

import junit.framework.TestCase;

import org.apache.velocity.context.Context;

import com.spun.util.velocity.ContextAware;
import com.spun.util.velocity.VelocityParser;

public class VelocityNullSetTest extends TestCase implements ContextAware
{
  /***********************************************************************/
  public void ptestArray() throws Exception
  {
    assertEquals("125", VelocityParser.parseString("#foreach($s in $array)$!s#end", this));
    //      assertEquals("12nullnull5", VelocityParser.parseString("#foreach($s in $array)$s#end", this));
  }
  /***********************************************************************/
  public void testField() throws Exception
  {
    //assertEquals("not null, null", VelocityParser.parseString("#set($s = $value)$s,#set($s = $nullValue)$s", this));
  }
  /***********************************************************************/
  public void setupContext(Context context)
  {
    context.put("value", "not null");
    context.put("nullValue", null);
    context.put("array", new String[]{"1", "2", null, null, "5"});
  }
}
/***********************************************************************/
/***********************************************************************/
