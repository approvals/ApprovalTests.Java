package com.spun.util.servlets.tests;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletOutputStream;

public class MockServletOutputStream extends ServletOutputStream
{
  private OutputStream output;
  MockServletOutputStream(OutputStream output)
  {
    this.output = output;
  }
  @Override
  public void write(int b) throws IOException
  {
    output.write(b);
  }
}
