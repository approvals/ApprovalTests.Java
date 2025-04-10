package org.approvaltests.internal.logs;

import com.spun.util.io.FileUtils;
import org.lambda.utils.Once;

import java.io.File;

public class ApprovedFileLog
{
  static
  {
    FileUtils.writeFile(get(), "");
    Once.runAsync(() -> LoggingUtils.downloadScriptIfMissing("remove_abandoned_files"));
  }
  public static File get()
  {
    File file = new File(LoggingUtils.getTempDirectory() + "/.approved_files.log");
    FileUtils.createIfNeeded(file.getAbsolutePath());
    return file;
  }
  public static void log(File file)
  {
    File log = get();
    FileUtils.appendToFile(log, file.getAbsolutePath() + "\n");
  }
}
