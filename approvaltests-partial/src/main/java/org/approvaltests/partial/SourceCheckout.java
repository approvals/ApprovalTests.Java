package org.approvaltests.partial;

import java.io.File;
import java.io.IOException;

public class SourceCheckout
{
  public static final String REPOSITORY = "https://github.com/approvals/ApprovalTests.Java.git";
  public static File cloneTag(String version, File directory)
  {
    return cloneTag(REPOSITORY, version, directory);
  }

  public static File cloneTag(String repository, String version, File directory)
  {
    if (new File(directory, ".git").exists())
    {
      run("git", "-C", directory.getAbsolutePath(), "reset", "--hard", "--quiet");
      run("git", "-C", directory.getAbsolutePath(), "clean", "-fdq");
    }
    else
    {
      directory.getParentFile().mkdirs();
      run("git", "-c", "advice.detachedHead=false", "clone", "--quiet", "--depth", "1", "--branch", version,
          repository, directory.getAbsolutePath());
    }
    return directory;
  }

  private static void run(String... command)
  {
    try
    {
      int exitCode = new ProcessBuilder(command).inheritIO().start().waitFor();
      if (exitCode != 0)
      { throw new RuntimeException(String.join(" ", command) + " failed with exit code " + exitCode); }
    }
    catch (IOException | InterruptedException e)
    {
      throw new RuntimeException(e);
    }
  }
}
