package org.approvaltests;

import com.spun.util.SystemUtils;
import com.spun.util.io.FileUtils;
import com.spun.util.io.NetUtils;

import java.io.File;

import static org.approvaltests.ApprovedFileLog.APPROVAL_TEMP_DIRECTORY;

public class FailedFileLog
{
  private static boolean downloadedScriptCheck = false;
  static
  {
    FileUtils.writeFile(get(), "");
  }
  private static void downloadApproveAllScriptIfMissing()
  {
    if (downloadedScriptCheck)
    { return; }
    downloadedScriptCheck = true;
    try
    {
      String extension = SystemUtils.isWindowsEnvironment() ? ".bat" : ".sh";
      File script = new File(APPROVAL_TEMP_DIRECTORY + "/approve_all" + extension);
      if (!script.exists())
      {
        String github = "https://raw.githubusercontent.com/approvals/ApprovalTests.Java/refs/heads/master/";
        String file = "resources/approve_all" + extension;
        FileUtils.writeFile(script, NetUtils.loadWebPage(github + file, null));
        script.setExecutable(true);
      }
    }
    catch (Exception e)
    {
      // do nothing
    }
  }
  public static File get()
  {
    File file = new File(APPROVAL_TEMP_DIRECTORY + "/.failed_comparison.log");
    FileUtils.createIfNeeded(file.getAbsolutePath());
    return file;
  }
  public static void log(File received, File approved)
  {
    downloadApproveAllScriptIfMissing();
    File log = get();
    FileUtils.appendToFile(log,
        String.format("%s -> %s\n", received.getAbsolutePath(), approved.getAbsolutePath()));
  }
  public static void touch()
  {
    // Allows static initializer to be called
  }
}
