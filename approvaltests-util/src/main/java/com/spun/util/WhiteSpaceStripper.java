package com.spun.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.spun.util.logger.SimpleLogger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class WhiteSpaceStripper
{
  public static void stripFolder(File dir)
  {
    stripFolder(dir, true);
  }
  public static void stripFolder(File dir, boolean recursive)
  {
    if (!dir.isDirectory())
    {
      SimpleLogger.warning("File is not a Directory - " + dir.toString());
      return;
    }
    File[] files = dir.listFiles(new WhiteSpaceFileFilter());
    for (int i = 0; i < files.length; i++)
    {
      if (files[i].isDirectory())
      {
        SimpleLogger.event("Scanning Directory -" + files[i].getName());
        if (recursive)
          stripFolder(files[i], recursive);
      }
      else
      {
        stripFile(files[i]);
      }
    }
    //    My_System.markerOut("WhiteSpaceStripper:stripFolder");
  }
  public static void stripFile(String file)
  {
    stripFile(new File(file));
  }
  public static void stripFile(File file)
  {
    if (!file.isFile())
    {
      SimpleLogger.warning("File is not a File - " + file.toString());
      return;
    }
    if (!file.canWrite())
    {
      SimpleLogger.event("File '" + file.toString() + "' is readonly");
      return;
    }
    else
    {
      try
      {
        String contents = readFile(file);
        String stripped = stripWhiteSpace(contents);
        writeFile(file, stripped);
      }
      catch (IOException e)
      {
        SimpleLogger.warning(e);
      }
    }
  }
  public static String stripWhiteSpace(String text)
  {
    StringBuffer newText = new StringBuffer();
    boolean whitespace = false;
    int num = text.length();
    char whiteSpaceChar = ' ';
    for (int i = 0; i < num; i++)
    {
      char c = text.charAt(i);
      switch (c)
      {
        case '\n' :
          whiteSpaceChar = '\n';
          whitespace = true;
          break;
        case '\t' :
        case ' ' :
          whitespace = true;
          break;
        default :
          if (whitespace)
          {
            whitespace = false;
            newText.append(whiteSpaceChar);
            whiteSpaceChar = ' ';
          }
          newText.append(c);
          break;
      }
    }
    return newText.toString();
  }
  public static String stripBlankLines(String text)
  {
    StringBuffer newText = new StringBuffer();
    boolean inWhiteSpace = true;
    int num = text.length();
    String saving = "";
    for (int i = 0; i < num; i++)
    {
      char c = text.charAt(i);
      switch (c)
      {
        case '\r' :
        case '\n' :
          if (!inWhiteSpace)
          {
            newText.append(saving);
          }
          inWhiteSpace = true;
          if (!"\r".equals(saving))
          {
            saving = "";
          }
          break;
        case '\t' :
        case ' ' :
          break; // donothing;  
        default :
          inWhiteSpace = false;
          break;
      }
      saving += c;
    }
    if (!inWhiteSpace)
    {
      newText.append(saving);
    }
    return newText.toString();
  }
  private static String readFile(File file) throws IOException
  {
    try (BufferedReader reader = Files.newBufferedReader(file.toPath()))
    {
      StringBuilder output = new StringBuilder();
      while (reader.ready())
      {
        output.append(reader.readLine());
        output.append("\n");
      }
      return output.toString();
    }
  }
  private static void writeFile(File file, String text) throws IOException
  {
    try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8))
    {
      writer.write(text);
    }
  }
}
