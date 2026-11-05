package org.approvaltests.partial;

import java.io.File;

public class SourceCheckout
{
  public static final String REPOSITORY = "https://github.com/approvals/ApprovalTests.Java.git";
  public static File cloneTag(String version, File directory)
  {
    return cloneTag(REPOSITORY, version, directory);
  }

  public static File cloneTag(String repository, String version, File directory)
  {
    File absolute = directory.getAbsoluteFile();
    if (new File(absolute, ".git").exists())
    {
      Shell.run(absolute, "git", "fetch", "--quiet", "--force", "--depth", "1", "origin", "tag", version);
      Shell.run(absolute, "git", "-c", "advice.detachedHead=false", "checkout", "--quiet", "--force", version);
      Shell.run(absolute, "git", "reset", "--hard", "--quiet", version);
      Shell.run(absolute, "git", "clean", "-fdq");
    }
    else
    {
      absolute.getParentFile().mkdirs();
      Shell.run(absolute.getParentFile(), "git", "-c", "advice.detachedHead=false", "clone", "--quiet", "--depth",
          "1", "--branch", version, repository, absolute.getPath());
    }
    return absolute;
  }

  public static File cloneBranch(String branch, File directory)
  {
    File absolute = directory.getAbsoluteFile();
    if (new File(absolute, ".git").exists())
    {
      Shell.run(absolute, "git", "fetch", "--quiet", "--depth", "1", "origin", branch);
      Shell.run(absolute, "git", "-c", "advice.detachedHead=false", "checkout", "--quiet", "--force", "--detach",
          "FETCH_HEAD");
      Shell.run(absolute, "git", "reset", "--hard", "--quiet", "FETCH_HEAD");
      Shell.run(absolute, "git", "clean", "-fdq");
    }
    else
    {
      absolute.getParentFile().mkdirs();
      Shell.run(absolute.getParentFile(), "git", "clone", "--quiet", "--depth", "1", "--branch", branch, REPOSITORY,
          absolute.getPath());
    }
    return absolute;
  }

  public static String shortCommit(File checkout)
  {
    return Shell.output(checkout, "git", "rev-parse", "--short", "HEAD").trim();
  }
}
