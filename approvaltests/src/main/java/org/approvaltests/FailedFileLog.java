package org.approvaltests;

import com.spun.util.SystemUtils;
import com.spun.util.io.FileUtils;
import com.spun.util.io.NetUtils;

import java.io.File;

import static org.approvaltests.ApprovedFileLog.APPROVAL_TEMP_DIRECTORY;

public class FailedFileLog
{
  static
  {
    FileUtils.writeFile(get(), "");
    downloadApproveAllScriptIfMissing();
  }
  private static void downloadApproveAllScriptIfMissing()
  {
    try
    {
      if (SystemUtils.isWindowsEnvironment())
      {
        String url = "https://raw.githubusercontent.com/approvals/ApprovalTests.Java/refs/heads/master/resources/approve_all.bat";
        File batScript = new File(APPROVAL_TEMP_DIRECTORY + "/approve_all.bat");
        if (!batScript.exists())
        {
          FileUtils.writeFile(batScript, NetUtils.loadWebPage(url, null));
        }
      }
      else
      {
        String url = "https://raw.githubusercontent.com/approvals/ApprovalTests.Java/refs/heads/master/resources/approve_all.sh";
        File bashScript = new File(APPROVAL_TEMP_DIRECTORY + "/approve_all.sh");
        if (!bashScript.exists())
        {
          FileUtils.writeFile(bashScript, NetUtils.loadWebPage(url, null));
          bashScript.setExecutable(true);
        }
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
    File log = get();
    FileUtils.appendToFile(log,
        String.format("%s -> %s\n", received.getAbsolutePath(), approved.getAbsolutePath()));
  }
  public static void touch()
  {
    // Allows static initializer to be called
  }
}
