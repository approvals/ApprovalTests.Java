package com.spun.util.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.spun.util.StringUtils;

/**
  * A static class of convence functions for Files
  **/
public class TabDelimitedFileReader
{
  private BufferedReader reader = null;
  private String lastRead = null;
  private boolean trim = false;
  /************************************************************************/
  public TabDelimitedFileReader(String absoluteFileName, boolean trim) throws FileNotFoundException
  {
    this.reader = new BufferedReader(new FileReader(absoluteFileName));
    this.trim = trim;
  }
  /***********************************************************************/
  public boolean next() throws IOException
  {
    return prepNext() != null;
  }
  /***********************************************************************/
  public String prepNext() throws IOException
  {
    if (reader == null) { return null; }
    lastRead = reader.readLine();
    if (lastRead == null)
    {
      reader.close();
      reader = null;
    }
    return lastRead;
  }
  /***********************************************************************/
  public String[] readLine(int minimumIndexReturned) throws IOException
  {
    if (lastRead == null)
    {
      if (prepNext() == null)
      {
        return null;
      }
    }
    String[] found = StringUtils.split(lastRead, "\t", trim);
    clean(found);
    if (found.length < minimumIndexReturned)
    {
      String[] temp = new String[minimumIndexReturned];
      System.arraycopy(found, 0, temp, 0, found.length);
      found = temp;
    }
    lastRead = null;
    return found;
  }
  /***********************************************************************/
  private void clean(String[] found)
  {
    for (int i = 0; i < found.length; i++)
    {
      if (found[i] != null && found[i].startsWith("\"") && found[i].endsWith("\""))
      {
        found[i] = found[i].substring(1, found[i].length()-1);
        found[i] = found[i].replaceAll("\"\"", "\"");
      }
    }
  }
  /***********************************************************************/
  /************************************************************************/
  
}