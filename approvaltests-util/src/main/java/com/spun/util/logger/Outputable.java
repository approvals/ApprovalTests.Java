package com.spun.util.logger;

import com.spun.util.ObjectUtils;

import java.io.IOException;

public class Outputable implements Output
{
  private Appendable out;
  public Outputable(Appendable out)
  {
    this.out = out;
  }
  @Override
  public Output append(CharSequence text)
  {
    try
    {
      out.append(text);
    }
    catch (IOException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
    return this;
  }
}
