package com.spun.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class DualOutputStream extends OutputStream
{
  private FileOutputStream o;
  private PrintStream      display = System.out;
  public DualOutputStream()
  {
    super();
  }
  public void setOutputStream(String outfile) throws SecurityException, Exception
  {
    try
    {
      o = new FileOutputStream(outfile);
      display.println("output being redirected to: " + outfile);
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  public void write(int b) throws IOException
  {
    try
    {
      o.write(b);
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
    display.write(b);
  }
  public void write(byte b[]) throws IOException
  {
    try
    {
      o.write(b);
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
    display.println(b);
    flush();
  }
  public void write(byte b[], int off, int len) throws IOException
  {
    try
    {
      o.write(b, off, len);
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
    display.write(b, off, len);
  }
  public void flush() throws IOException
  {
    try
    {
      o.flush();
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
    display.flush();
  }
  public void close() throws IOException
  {
    try
    {
      o.close();
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
    display.close();
  }
}