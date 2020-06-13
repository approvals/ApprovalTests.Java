package com.spun.util.velocity;

import org.apache.velocity.context.Context;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

public class VelocityNullSetTest implements ContextAware
{
  @Test
  public void testArray() throws Exception
  {
    Approvals.verify(VelocityParser.parseString("#foreach($s in $array)$s, #end", this));
    Approvals.verify(VelocityParser.parseString("#foreach($s in $array)$!s, #end", this));
  }
  @Override
  public void setupContext(Context context)
  {
    context.put("value", "not null");
    context.put("nullValue", null);
    context.put("array", new String[]{"1", "2", null, null, "5"});
  }
}
