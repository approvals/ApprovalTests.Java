package org.approvaltests;

import com.spun.util.io.FileUtils;

import java.io.File;

import static org.approvaltests.ApprovedFileLog.APPROVAL_TEMP_DIRECTORY;

public class FailedFileLog
{
  static
  {
    FileUtils.writeFile(get(), "");
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
    FileUtils.appendToFile(log, String.format("%s -> %s\n", received.getAbsolutePath(), approved.getAbsolutePath()));
  }

  public static void touch() {
    // Allows static initializer to be called
  }
}
