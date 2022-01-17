package org.approvaltests.reporters.linux;

import com.spun.util.io.FileUtils;
import org.approvaltests.reporters.DiffInfo;
import org.approvaltests.reporters.GenericDiffReporter;

import java.io.File;

public class ReportByCreatingDiffFile extends GenericDiffReporter
{
  private static DiffInfo                      info     = new DiffInfo("/usr/bin/diff",
      GenericDiffReporter.TEXT_FILE_EXTENSIONS);
  public static final ReportByCreatingDiffFile INSTANCE = new ReportByCreatingDiffFile();
  public ReportByCreatingDiffFile()
  {
    super(info);
  }
  protected void processOutput(String received, Process process)
  {
    String extensionWithDot = FileUtils.getExtensionWithDot(received);
    String diffFile = received.replace(".received" + extensionWithDot, ".diff");
    FileUtils.writeFile(new File(diffFile), process.getInputStream());
  }

  @Override
  protected void preventProcessFromClosing(ProcessBuilder builder) {
    // prevent writing to /dev/null
  }
}
