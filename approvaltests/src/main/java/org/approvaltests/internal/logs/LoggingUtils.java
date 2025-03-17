package org.approvaltests.internal.logs;

import com.spun.util.SystemUtils;
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
      String extension = SystemUtils.isWindowsEnvironment() ? ".bat" : ".sh";
      File script = new File(getTempDirectory() + "/" + scriptName + extension);
      if (!script.exists())
      {
        String github = "https://raw.githubusercontent.com/approvals/ApprovalTests.Java/refs/heads/master/";
        String file = "resources/" + scriptName + extension;
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
    File approvalTestsTempDir = new File(".approval_tests_temp");
    FileUtils.writeFile(new File(approvalTestsTempDir + "/.gitignore"), "*");
    return approvalTestsTempDir;
  }
}
