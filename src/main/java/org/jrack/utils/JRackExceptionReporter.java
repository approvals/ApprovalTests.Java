package org.jrack.utils;

import java.util.Map;
import org.jrack.JRack;
import org.jrack.RackResponse;
import org.jrack.RackResponseUtils;
import com.spun.util.parser.TemplateError;
import com.spun.util.velocity.ContextAware;
import com.spun.util.velocity.VelocityParser;

public class JRackExceptionReporter implements JRack
{
  private final JRack rack;

  public JRackExceptionReporter(JRack rack)
  {
    this.rack = rack;
    
  }

  @Override
  public RackResponse call(Map<String, Object> environment) throws Exception
  {
    try
    {
      return rack.call(environment);
      
    } catch (Throwable e)
    {
      return RackResponseUtils.standardHtml(processError(e));
    }
  }

  private String processError(Throwable e)
  {
    ContextAware.ContextAwareMap context = new ContextAware.ContextAwareMap("error",  new TemplateError(e, this));
    return VelocityParser.parseFromClassPath(getClass(), "error_message.html", context);
  }
}
