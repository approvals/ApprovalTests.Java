package com.spun.util.io;

import com.spun.util.StringUtils;
import com.spun.util.logger.SimpleLogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommaDelimitedFileParser
{
  public static String[][] parse(File databaseFile)
  {
    return parse(FileUtils.readFileWithSuppressedExceptions(databaseFile));
  }

  public static String[][] parse(String data)
  {
    return parse(new StringReader(data));
  }

  public static String[][] parse(Reader data)
  {
    try
    {
      List<String[]> records = new ArrayList<>();
      try (BufferedReader reader = new BufferedReader(data))
      {
        boolean done = false;
        StringBuffer pastLines = null;
        while (!done)
        {
          String line = reader.readLine();
          if (line == null)
          {
            done = true;
          }
          else
          {
            if (pastLines != null)
            {
              pastLines.append("\n");
              pastLines.append(line);
              line = pastLines.toString();
            }
            String[] parseLine = parseLine(line);
            if (parseLine == null)
            {
              if (pastLines == null)
              {
                pastLines = new StringBuffer(line);
              }
            }
            else
            {
              records.add(parseLine);
              pastLines = null;
            }
          }
        }
      }
      return records.toArray(new String[records.size()][]);
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  private static String[] parseLine(String line) throws IOException
  {
    String[] rawtokens = StringUtils.split(line, ",", false);
    boolean in = false;
    List<String> tokens = new ArrayList<String>();
    String fullToken = null;
    for (int i = 0; i < rawtokens.length; i++)
    {
      String string = rawtokens[i];
      if (in)
      {
        fullToken += ",";
        fullToken += string;
        if (fullToken.endsWith("\""))
        {
          tokens.add(clearQuotes(fullToken));
          in = false;
        }
      }
      else
      {
        if (string.startsWith("\"") && !string.endsWith("\""))
        {
          fullToken = string;
          in = true;
        }
        else
        {
          tokens.add(clearQuotes(string));
        }
      }
    }
    return in ? null : StringUtils.toArray(tokens);
  }

  private static String clearQuotes(String string)
  {
    String s = (string.startsWith("\"") && string.endsWith("\""))
        ? string.substring(1, string.length() - 1)
        : string;
    return s.trim();
  }

  public static Map<String, String>[] parseToMap(File file)
  {
    try
    {
      return parseToMap(FileUtils.readFileWithSuppressedExceptions(file));
    }
    catch (RuntimeException e)
    {
      SimpleLogger.variable("Filename", file);
      throw e;
    }
  }

  public static Map<String, String>[] parseToMap(String data)
  {
    return parseToMap(new StringReader(data));
  }

  public static Map<String, String>[] parseToMap(Reader data)
  {
    String[][] out = parse(data);
    Map<String, String>[] maps = new HashMap[out.length - 1];
    String[] labels = out[0];
    for (int i = 1; i < out.length; i++)
    {
      Map<String, String> map = new HashMap<String, String>(labels.length);
      for (int j = 0; j < labels.length; j++)
      {
        map.put(labels[j], out[i][j]);
      }
      maps[i - 1] = map;
    }
    return maps;
  }
}
