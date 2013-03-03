package org.jrack.tests;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import org.jrack.JRack;
import org.jrack.RackResponse;
import org.jrack.RackResponseUtils;
import com.spun.util.velocity.ContextAware;
import com.spun.util.velocity.VelocityParser;

public class MammogramRack implements JRack
{
  @Override
  public RackResponse call(Map<String, Object> input) throws Exception
  {
    ContextAware.ContextAwareMap context = new ContextAware.ContextAwareMap(
        "input", input);
    context.put("keys", sort(input.keySet()));
    String parsed = VelocityParser.parseFromClassPath(getClass(),
        "mammorgram_rack.htm", context);
    return RackResponseUtils.standardHtml(parsed);
  }

  public static Object[] sort(Set set)
  {
    Object[] keySet = set.toArray();
    Arrays.sort(keySet);
    return keySet;
  }
}
