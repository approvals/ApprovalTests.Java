package org.approvaltests.testcommitrevert;

import java.io.File;
import java.io.IOException;

import com.spun.util.ObjectUtils;
import com.spun.util.io.FileUtils;
import com.spun.util.logger.SimpleLogger;

public class FasterCommitDialog
{
  public static String getCommitMessageViaAppleScript() throws Error
  {
    try
    {
      return getCommitMessage();
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  private static String getCommitMessage() throws IOException, InterruptedException
  {
    String scriptRunner = "/usr/bin/osascript";
    if (!new File(scriptRunner).exists())
    {
      return ArlosGitNotationPrompt.display();
    }
    else
    {
      Process exec = Runtime.getRuntime().exec(scriptRunner + " " + ensureScriptExists());
      exec.waitFor();
      return FileUtils.readStream(exec.getInputStream());
    }
  }
  private static String ensureScriptExists() throws IOException
  {
    String script = getTempDirectory().getAbsolutePath() + "/Dialog.scpt";
    File file = new File(script);
    SimpleLogger.variable(file.getAbsolutePath());
    if (!file.exists())
    {
      writeScript(file);
    }
    return script;
  }
  private static File getTempDirectory() throws IOException
  {
    File createTempFile = File.createTempFile("notused", ".txt").getParentFile();
    return createTempFile;
  }
  private static void writeScript(File file)
  {
    String code = ("display dialog 'Commit Message: ' default answer '' buttons {'Commit'} default button 1"
        + "\n\n text returned of the result\n").replace('\'', '"');
    SimpleLogger.variable(code);
    FileUtils.writeFileQuietly(file, code);
  }
}
