package org.teachingextensions.setup;

import java.io.IOException;

import com.spun.util.ObjectUtils;

public class CommandLineUtils
{
  public static void launch(String commandLine, String... formattingArguments)
  {
    try
    {
      String command = String.format(commandLine, (Object[]) formattingArguments);
      Process exec = Runtime.getRuntime().exec(command);
    }
    catch (IOException e)
    {
      ObjectUtils.throwAsError(e);
    }
  }
}
