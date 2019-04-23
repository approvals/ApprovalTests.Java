package org.approvaltests.legacycode;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

import com.spun.util.ObjectUtils;
import com.spun.util.StringUtils;
import com.spun.util.io.FileUtils;

public class TestCommitRevert
{
  public static boolean PRINT_ONLY = false;
  static int            failures   = 0;
  @Rule
  public MethodRule     watchman   = new TestWatchman()
                                   {
                                     public void starting(FrameworkMethod method)
                                     {
                                       // nothing
                                     }
                                     public void succeeded(FrameworkMethod method)
                                     {
                                       // nothing
                                     }
                                     public void failed(Throwable e, FrameworkMethod method)
                                     {
                                       failures++;
                                     }
                                   };
  @AfterClass
  public static void after() throws IOException
  {
    File gitDir = getHeadOfGit();
    if (gitDir == null)
    {
      System.out.println("No .git repo found at " + new File(".").getAbsolutePath());
      return;
    }
    if (failures == 0)
    {
      commit(gitDir);
    }
    else
    {
      revertGit(gitDir);
    }
  }
  private static File getHeadOfGit() throws IOException
  {
    File file = new File(".").getCanonicalFile();
    while (true)
    {
      //System.out.println("checking " + file.getAbsolutePath());
      File gitFile = new File(file, ".git");
      if (gitFile.exists())
      {
        return file;
      }
      else
      {
        file = file.getParentFile();
        if (file == null) { return null; }
      }
    }
  }
  private static void commit(File gitDir)
  {
    if (isGitEmpty(gitDir))
    {
      System.out.println("Nothing to commit");
      return;
    }
    String message = JOptionPane.showInputDialog("Test Passed! Please enter a commit message ");
    if (!StringUtils.isEmpty(message))
    {
      runOnConsole(gitDir, "git", "add", "-A");
      runOnConsole(gitDir, "git", "commit", "-m", '"' + message + '"');
    }
  }
  private static boolean isGitEmpty(File gitDir)
  {
    runOnConsole(gitDir, new String[]{"git", "status"});
    try
    {
      Process p = Runtime.getRuntime().exec(new String[]{"git", "status"}, null, gitDir);
      p.waitFor();
      String result = FileUtils.readStream(p.getInputStream());
      return result.contains("nothing to commit");
    }
    catch (Exception e)
    {
      ObjectUtils.throwAsError(e);
    }
    return false;
  }
  private static void revertGit(File gitDir)
  {
    runOnConsole(gitDir, "git", "clean", "-fd");
    runOnConsole(gitDir, "git", "reset", "--hard", "HEAD");
    String helpMessage = "Test Failed, reverting...\n\n Remember to auto refresh \n preferences > general>  workspace > ✓ refresh using native Hooks";
    System.out.println(helpMessage);
  }
  private static void runOnConsole(File workingDir, String... cmdArgs) throws Error
  {
    //System.out.println(Arrays.toString(cmdArgs));
    if (PRINT_ONLY) { return; }
    try
    {
      Process p = Runtime.getRuntime().exec(cmdArgs, null, workingDir);
      p.waitFor();
      System.out.println(com.spun.util.io.FileUtils.readStream(p.getInputStream()));
      System.out.println(com.spun.util.io.FileUtils.readStream(p.getErrorStream()));
    }
    catch (Exception e)
    {
      ObjectUtils.throwAsError(e);
    }
  }
}
