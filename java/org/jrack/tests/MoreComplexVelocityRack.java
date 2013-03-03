package org.jrack.tests;

import java.util.Map;

import org.apache.velocity.context.Context;
import org.jrack.JRack;
import org.jrack.RackResponse;
import org.jrack.RackResponseUtils;

import com.spun.util.NumberUtils;
import com.spun.util.StringUtils;
import com.spun.util.velocity.ContextAware;
import com.spun.util.velocity.VelocityParser;

public class MoreComplexVelocityRack implements JRack
{
  public static class MoreComplexVelocity implements ContextAware
  {
    private String name;
    private int    age;
    public String init(String name, int age)
    {
      this.name = name;
      this.age = age;
      return VelocityParser.parseFromClassPath(getClass(), "more_complex_velocity.html", this);
    }
    @Override
    public void setupContext(Context context)
    {
      context.put("head", this);
    }
    public String getName()
    {
      return name;
    }
    public int getAge()
    {
      return age;
    }
  }
  @Override
  public RackResponse call(Map<String, Object> input) throws Exception
  {
    MoreComplexVelocity process = new MoreComplexVelocity();
    String name = StringUtils.loadNullableString((String) input.get("name"));
    int age = NumberUtils.load((String) input.get("age"), 0);
    return RackResponseUtils.standardHtml(process.init(name, age));
  }
}
