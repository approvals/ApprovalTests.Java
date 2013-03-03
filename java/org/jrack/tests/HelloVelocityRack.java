package org.jrack.tests;

import java.util.Map;

import org.jrack.JRack;
import org.jrack.RackResponse;
import org.jrack.RackResponseUtils;

import com.spun.util.velocity.ContextAware;
import com.spun.util.velocity.VelocityParser;

public class HelloVelocityRack implements JRack
{
  @Override
  public RackResponse call(Map<String, Object> input) throws Exception
  {
    String parsed = VelocityParser.parseString("Hello $name", new ContextAware.ContextAwareMap("name", "fred"));
    return RackResponseUtils.standardHtml(parsed);
  }
}
