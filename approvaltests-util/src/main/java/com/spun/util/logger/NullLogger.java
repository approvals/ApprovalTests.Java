package com.spun.util.logger;

public class NullLogger implements Appendable
{
  @Override
  public Appendable append(CharSequence csq)
  {
    return this;
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end)
  {
    return this;
  }

  @Override
  public Appendable append(char c)
  {
    return this;
  }
}
