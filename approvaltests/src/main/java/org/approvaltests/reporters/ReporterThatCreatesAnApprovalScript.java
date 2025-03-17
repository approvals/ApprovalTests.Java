package org.approvaltests.reporters;

import com.spun.util.SystemUtils;
import com.spun.util.io.FileUtils;
import com.spun.util.logger.SimpleLogger;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.internal.logs.LoggingUtils;

import java.io.File;

public class ReporterThatCreatesAnApprovalScript implements ApprovalFailureReporter
{
  private static String fileName   = "approval_script";
  private static File   scriptFile = null;
  static
  {
    initializeFile();
  }
  private static void initializeFile()
  {
    if (scriptFile != null)
    { return; }
    if (SystemUtils.isWindowsEnvironment())
    {
      initializeWindows();
    }
    else
    {
      initializeLinux();
    }
    SimpleLogger.event("Created approval script:\n" + scriptFile.getAbsolutePath());
  }
  private static void initializeLinux()
  {
    scriptFile = new File(LoggingUtils.getTempDirectory() + "/" + fileName + ".sh");
    FileUtils.createIfNeeded(scriptFile.getAbsolutePath());
    FileUtils.writeFile(scriptFile, "#!/bin/bash\n");
    scriptFile.setExecutable(true);
  }
  private static void initializeWindows()
  {
    scriptFile = new File(LoggingUtils.getTempDirectory() + "\\" + fileName + ".bat");
    FileUtils.createIfNeeded(scriptFile.getAbsolutePath());
    FileUtils.writeFile(scriptFile, "");
  }
  @Override
  public boolean report(String received, String approved)
  {
    String commandLine = ClipboardReporter.getCommandLine(received, approved);
    FileUtils.appendToFile(scriptFile, commandLine + "\n");
    return true;
  }
}
