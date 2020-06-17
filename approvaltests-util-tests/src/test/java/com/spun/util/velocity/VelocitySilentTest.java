package com.spun.util.velocity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.velocity.context.Context;
import org.junit.jupiter.api.Test;

public class VelocitySilentTest implements ContextAware
{
  @Test
  public void testMethod() throws Exception
  {
    assertEquals("", VelocityParser.parseString("$!main.toString()", this));
  }
  @Test
  public void testField() throws Exception
  {
    // using the toString implied method
    assertEquals("", VelocityParser.parseString("$!main", this));
  }
  public Object getInstance()
  {
    return this;
  }
  public String toString()
  {
    return null;
  }
  @Override
  public void setupContext(Context context)
  {
    context.put("main", this);
    context.put("nullValue", null);
  }
}
