package com.spun.util.velocity.tests;

import org.apache.velocity.context.Context;
import org.approvaltests.Approvals;

import com.spun.util.velocity.ContextAware;
import com.spun.util.velocity.VelocityParser;

import junit.framework.TestCase;

public class VelocityNullSetTest extends TestCase implements ContextAware
{
  
  public void testArray() throws Exception
  {
    //Approvals.verify(VelocityParser.parseString("#foreach($s in $array)$!s, #end", this));
    Approvals.verify(VelocityParser.parseString("#foreach($s in $array)$s, #end", this));
  }
  
  public void ptestField() throws Exception
  {
    assertEquals("not null, null",
        VelocityParser.parseString("#set($s = $value)$s,#set($s = $nullValue)$s", this));
  }
  
  public void setupContext(Context context)
  {
    context.put("value", "not null");
    context.put("nullValue", null);
    context.put("array", new String[]{"1", "2", null, null, "5"});
  }
}


