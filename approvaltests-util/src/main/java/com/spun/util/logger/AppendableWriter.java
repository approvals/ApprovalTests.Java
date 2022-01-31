package com.spun.util.logger;

import com.spun.util.ObjectUtils;

import java.io.Writer;

public class AppendableWriter extends Writer
{
  private final Appendable appendable;
  public AppendableWriter(Appendable appendable)
  {
    this.appendable = appendable;
  }
  public void write(char[] chars, int offset, int length)
  {
    ObjectUtils.throwAsError(() -> appendable.append(new String(chars), offset, offset + length));
  }
  public void write(int i)
  {
    ObjectUtils.throwAsError(() -> appendable.append((char) i));
  }
  public void flush()
  {
  }
  public void close()
  {
  }
}
