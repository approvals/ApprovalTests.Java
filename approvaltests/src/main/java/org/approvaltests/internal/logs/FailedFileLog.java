package org.approvaltests.internal.logs;

import com.spun.util.io.FileUtils;
import org.lambda.utils.Once;

import java.io.File;

public class FailedFileLog
{
  static
  {
    FileUtils.writeFile(get(), "");
    downloadApproveAllScriptIfMissing();
  }
  private static void downloadApproveAllScriptIfMissing()
  {
    Once.runAsync(() -> LoggingUtils.downloadScriptIfMissing("approve_all"));
  }
  public static File get()
  {
    File file = new File(LoggingUtils.getTempDirectory() + "/.failed_comparison.log");
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
