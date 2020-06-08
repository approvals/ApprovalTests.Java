package com.spun.util;

/*
 * @(#)ByteArrayDataSource.java	1.1 00/01/30
 *
 * Copyright 1998-2000 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
import java.io.*;
import javax.activation.*;

/**
 * Comment By LLewellyn: This is a needed class for using mail. For some reason wasn't 
 * made as part of the standard mail package.<P>
 *
 * A simple DataSource for demonstration purposes.
 * This class implements a DataSource from:
 *   an InputStream
 *  a byte array
 *   a String
 *
 * @author John Mani
 * @author Bill Shannon
 * @author Max Spivak
 **/
public class ByteArrayDataSource implements DataSource
{
  private byte[] data;          // data
  private String type;          // content-type
  private String name = "dummy";
  
  /* Create a DataSource from an input stream */
  public ByteArrayDataSource(InputStream is, String type)
  {
    this(is, type, null);
  }
  
  /* Create a DataSource from an input stream */
  public ByteArrayDataSource(InputStream is, String type, String name)
  {
    this.type = type;
    setName(name);
    try
    {
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      int ch;
      while ((ch = is.read()) != -1)
      {
        // XXX - must be made more efficient by
        // doing buffered reads, rather than one byte reads
        os.write(ch);
      }
      data = os.toByteArray();
    }
    catch (IOException ioex)
    {
    }
  }
  
  /* Create a DataSource from a byte array */
  public ByteArrayDataSource(byte[] data, String type)
  {
    this.data = data;
    this.type = type;
  }
  
  public void setName(String name)
  {
    this.name = (name == null) ? "dummy" : name;
  }
  
  /* Create a DataSource from a String */
  public ByteArrayDataSource(String data, String type)
  {
    this(data, type, null);
  }
  
  /* Create a DataSource from a String */
  public ByteArrayDataSource(String data, String type, String name)
  {
    setName(name);
    try
    {
      // Assumption that the string contains only ASCII
      // characters!  Otherwise just pass a charset into this
      // constructor and use it in getBytes()
      this.data = data.getBytes("iso-8859-1");
    }
    catch (UnsupportedEncodingException uex)
    {
    }
    this.type = type;
  }
  
  /**
   * Return an InputStream for the data.
   * Note - a new stream must be returned each time.
   */
  public InputStream getInputStream() throws IOException
  {
    if (data == null) { throw new IOException("no data"); }
    return new ByteArrayInputStream(data);
  }
  
  public OutputStream getOutputStream() throws IOException
  {
    throw new IOException("cannot do this");
  }
  
  public String getContentType()
  {
    return type;
  }
  
  public String getName()
  {
    return name;
  }
  
  
}