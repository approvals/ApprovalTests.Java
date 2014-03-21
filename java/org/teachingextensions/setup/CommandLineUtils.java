package org.teachingextensions.setup;

import java.io.IOException;

import com.spun.util.ObjectUtils;

public class CommandLineUtils
{
  public static void launch(String commandLine, String... formattingArguments)
  {
    try
    {
      Process exec = Runtime.getRuntime().exec(String.format(commandLine, formattingArguments));
    }
    catch (IOException e)
    {
      ObjectUtils.throwAsError(e);
    }
  }
}
