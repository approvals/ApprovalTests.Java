package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.GenericDiffReporter;

public class TortoiseImageDiffReporter extends GenericDiffReporter
{
  public static final TortoiseImageDiffReporter INSTANCE = new TortoiseImageDiffReporter();
  public TortoiseImageDiffReporter()
  {
    super("C:\\Program Files\\TortoiseSVN\\bin\\TortoiseIDiff.exe", "/left:%s /right:%s",
        TortoiseTextDiffReporter.MESSAGE, GenericDiffReporter.IMAGE_FILE_EXTENSIONS);
  }
}
