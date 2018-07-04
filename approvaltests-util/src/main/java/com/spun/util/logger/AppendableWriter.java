package com.spun.util.logger;

import java.io.IOException;
import java.io.Writer;

public class AppendableWriter extends Writer
{
  private final Appendable appendable;
  public AppendableWriter(Appendable appendable)
  {
    this.appendable = appendable;
  }
  public void write(char[] chars, int offset, int length) throws IOException
  {
    appendable.append(new String(chars), offset, offset + length);
  }
  public void write(int i) throws IOException
  {
    appendable.append((char) i);
  }
  public void flush()
  {
  }
  public void close()
  {
  }
}
