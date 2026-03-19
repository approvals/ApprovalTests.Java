package org.approvaltests.internal.logs;

import com.spun.util.ClassUtils;
import com.spun.util.io.FileUtils;
import com.spun.util.io.NetUtils;

import java.io.File;
import java.time.Duration;

public class LoggingUtils
{
  public static void downloadScriptIfMissing(String scriptName)
  {
    boolean disabled = "1".equals(System.getenv("APPROVALTESTS_DISABLE_SCRIPT_DOWNLOADS"));
    downloadScriptIfMissing(scriptName, disabled);
  }

  public static boolean downloadScriptIfMissing(String scriptName, boolean disabled)
  {
    if (disabled)
    { return false; }
    try
    {
      String extension = ".py";
      String file = scriptName + extension;
      File script = new File(getTempDirectory() + "/" + scriptName + extension);
      if (!script.exists())
      {
        String github = "https://raw.githubusercontent.com/approvals/ApprovalTests.CommonScripts/refs/heads/main/";
        FileUtils.writeFile(script, NetUtils.loadWebPage(github + file, null, Duration.ofSeconds(3)));
        script.setExecutable(true);
      }
      return true;
    }
    catch (Throwable e)
    {
      // do nothing
    }
    return false;
  }

  public static File getTempDirectory()
  {
    File approvalTestsTempDir = new File(ClassUtils.getProjectRootPath() + "/.approval_tests_temp");
    FileUtils.writeFile(new File(approvalTestsTempDir + "/.gitignore"), "*");
    return approvalTestsTempDir;
  }
}
