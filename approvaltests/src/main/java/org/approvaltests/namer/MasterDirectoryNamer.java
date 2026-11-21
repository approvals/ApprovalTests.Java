package org.approvaltests.namer;

import org.approvaltests.core.Options;

import java.io.File;

public class MasterDirectoryNamer implements ApprovalNamer
{
  private final ApprovalNamer namer;
  private final String        approvedFile;
  public MasterDirectoryNamer(File comparingFile, Options options)
  {
    this.namer = options.forFile().getNamer();
    this.approvedFile = comparingFile.getName();
  }

  public MasterDirectoryNamer(String approvedFile, ApprovalNamer namer)
  {
    this.approvedFile = approvedFile;
    this.namer = namer;
  }

  @Override
  public File getApprovedFile(String extensionWithDot)
  {
    String dirName = NamerUtils.getSourcePath(namer) + NamerUtils.getApprovalName(namer) + ".Files";
    return new File(dirName + File.separator + approvedFile);
  }

  @Override
  public File getReceivedFile(String extensionWithDot)
  {
    return namer.getReceivedFile(extensionWithDot);
  }
}
