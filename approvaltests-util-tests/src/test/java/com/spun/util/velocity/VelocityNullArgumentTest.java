package com.spun.util.velocity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.velocity.context.Context;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class VelocityNullArgumentTest implements ContextAware
{
  @Disabled("believe velocity needs to be configured to throw nulls")
  @Test
  public void testOverloadedMethodFound() throws Exception
  {
    assertEquals("you got null", VelocityParser.parseString("$object.getClass($nullValue)", this));
  }
  @Override
  public void setupContext(Context context)
  {
    context.put("object", this);
    context.put("nullValue", null);
  }
}
