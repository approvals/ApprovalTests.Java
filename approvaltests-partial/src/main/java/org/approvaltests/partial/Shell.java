package org.approvaltests.partial;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Shell
{
  public static void run(File directory, String... command)
  {
    try
    {
      int exitCode = new ProcessBuilder(command).directory(directory).inheritIO().start().waitFor();
      if (exitCode != 0)
      { throw new RuntimeException(String.join(" ", command) + " failed with exit code " + exitCode); }
    }
    catch (IOException | InterruptedException e)
    {
      throw new RuntimeException(e);
    }
  }

  public static String output(File directory, String... command)
  {
    try
    {
      Process process = new ProcessBuilder(command).directory(directory).redirectErrorStream(true).start();
      String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
      int exitCode = process.waitFor();
      if (exitCode != 0)
      { throw new RuntimeException(String.join(" ", command) + " failed with exit code " + exitCode + "\n" + output); }
      return output;
    }
    catch (IOException | InterruptedException e)
    {
      throw new RuntimeException(e);
    }
  }
}
