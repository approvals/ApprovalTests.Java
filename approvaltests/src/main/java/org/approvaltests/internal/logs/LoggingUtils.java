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
    }
    catch (Throwable e)
    {
      // do nothing
    }
  }
  public static File getTempDirectory()
  {
    File approvalTestsTempDir = new File(ClassUtils.getProjectRootPath() + "/.approval_tests_temp");
    FileUtils.writeFile(new File(approvalTestsTempDir + "/.gitignore"), "*");
    return approvalTestsTempDir;
  }
}
