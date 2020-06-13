package com.spun.util.velocity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.velocity.context.Context;
import org.junit.jupiter.api.Test;

public class VelocityTableNameTest implements ContextAware
{
  @Test
  public void testWTF() throws Exception
  {
    String template = "${main.getObjectName()}Metadata.TABLE_NAME, ";
    assertEquals("CompanyMetadata.TABLE_NAME, ", VelocityParser.parseString(template, this));
  }
  public String getObjectName()
  {
    return "Company";
  }
  @Override
  public void setupContext(Context context)
  {
    context.put("main", this);
  }
}
