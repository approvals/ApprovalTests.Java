package org.approvaltests;

import com.spun.util.io.FileUtils;

import java.io.File;

public class ApprovedFileLog
{
  public static final String APPROVAL_TEMP_DIRECTORY = ".approval_tests_temp";
  static
  {
    FileUtils.writeFile(get(), "");
  }
  public static File get()
  {
    File file = new File(APPROVAL_TEMP_DIRECTORY + "/.approved_files.log");
    FileUtils.createIfNeeded(file.getAbsolutePath());
    return file;
  }
  public static void log(File file)
  {
    File log = get();
    FileUtils.appendToFile(log, file.getAbsolutePath() + "\n");
  }
}
