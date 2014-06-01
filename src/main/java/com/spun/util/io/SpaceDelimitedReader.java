package com.spun.util.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import com.spun.util.MySystem;

/**
  * A static class of convence functions for Files
  **/
public class SpaceDelimitedReader
{
  private BufferedReader reader = null;
  private String lastRead = null;
  public boolean trim = false;
  /************************************************************************/
  public SpaceDelimitedReader(String input, boolean trim)
  {
    this.reader = new BufferedReader(new StringReader(input));
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
    MySystem.variable(lastRead);
    return lastRead;
  }
  /***********************************************************************/
  public String[] readLine(int i) throws IOException
  {
     return readLine(new int[] {i});
  }
  /***********************************************************************/
  public String[] readLine(int[] breakPoints) throws IOException
  {
    if (lastRead == null)
    {
      if (prepNext() == null)
      {
        return null;
      }
    }
    String[] found = splitStringAtPoints(breakPoints, lastRead, trim);
    
    lastRead = null;
    return found;
  }
  /***********************************************************************/
  public static String[] splitStringAtPoints(int[] breakPoints, String line, boolean trim)
  {
    if (line == null) { return null; }
    if (breakPoints == null) 
    {
      breakPoints = new int[0];
    }
    String[] found = new String[breakPoints.length + 1];
    int last = 0;
    for (int i = 0; i < breakPoints.length; i++)
    {
      found[i] = readStringPart(line, last, breakPoints[i], trim);
      last = breakPoints[i];
    }
    found[breakPoints.length] = readStringPart(line, last, line.length(), trim);
    return found;
  }
  /***********************************************************************/
  public static String readStringPart(String string, int start, int end, boolean trim)
  {
    if (start >= string.length()) { return null; }
    String found = string.substring(start, end);
    return trim ? found.trim() : found;
  }  
  /***********************************************************************/
  /***********************************************************************/
  
}