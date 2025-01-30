package org.approvaltests.internal.logs;

import com.spun.util.SystemUtils;
import com.spun.util.io.FileUtils;
import com.spun.util.io.NetUtils;

import java.io.File;

public class LoggingUtils
{
  public static final String APPROVAL_TEMP_DIRECTORY = ".approval_tests_temp";
  public static void downloadScriptIfMissing(String scriptName)
  {
    try
    {
      String extension = SystemUtils.isWindowsEnvironment() ? ".bat" : ".sh";
      File script = new File(APPROVAL_TEMP_DIRECTORY + "/" + scriptName + extension);
      if (!script.exists())
      {
        String github = "https://raw.githubusercontent.com/approvals/ApprovalTests.Java/refs/heads/master/";
        String file = "resources/" + scriptName + extension;
        FileUtils.writeFile(script, NetUtils.loadWebPage(github + file, null));
        script.setExecutable(true);
      }
    }
    catch (Exception e)
    {
      // do nothing
    }
  }
}
