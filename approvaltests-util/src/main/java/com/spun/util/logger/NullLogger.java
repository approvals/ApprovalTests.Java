package com.spun.util.logger;

import java.io.IOException;

public class NullLogger implements Appendable
{
  @Override
  public Appendable append(CharSequence csq) throws IOException
  {
    return this;
  }
  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException
  {
    return this;
  }
  @Override
  public Appendable append(char c) throws IOException
  {
    return this;
  }
}
