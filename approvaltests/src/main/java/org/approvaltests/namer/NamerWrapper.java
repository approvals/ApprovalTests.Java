package org.approvaltests.namer;

import org.approvaltests.writers.Writer;

import java.io.File;

public class NamerWrapper implements ApprovalNamer
{
  private GetApprovalName   approvalBaseName;
  private GetSourceFilePath sourceFilePath;
  public NamerWrapper(ApprovalNamer namer)
  {
    this.approvalBaseName = (GetApprovalName) namer;
    this.sourceFilePath = (GetSourceFilePath) namer;
  }

  public NamerWrapper(GetApprovalName approvalBaseName, GetSourceFilePath sourceFilePath)
  {
    this.approvalBaseName = unwrapGetApprovalName(approvalBaseName);
    this.sourceFilePath = unwrapGetSourceFilePath(sourceFilePath);
  }

  private static GetApprovalName unwrapGetApprovalName(GetApprovalName approvalBaseName)
  {
    if (approvalBaseName instanceof NamerWrapper)
    {
      NamerWrapper wr = (NamerWrapper) approvalBaseName;
      approvalBaseName = wr.approvalBaseName;
    }
    return approvalBaseName;
  }

  private static GetSourceFilePath unwrapGetSourceFilePath(GetSourceFilePath sourceFilePath)
  {
    if (sourceFilePath instanceof NamerWrapper)
    {
      NamerWrapper wr = (NamerWrapper) sourceFilePath;
      sourceFilePath = wr.sourceFilePath;
    }
    return sourceFilePath;
  }

  @Override
  public String getApprovalName()
  {
    return approvalBaseName.getApprovalName();
  }

  @Override
  public String getSourceFilePath()
  {
    return sourceFilePath.getSourceFilePath();
  }

  public void setApprovalBaseName(GetApprovalName approvalBaseName)
  {
    this.approvalBaseName = approvalBaseName;
  }

  public void setSourceFilePath(GetSourceFilePath getSourceFilePath)
  {
    this.sourceFilePath = getSourceFilePath;
  }

  public File getReceivedFile(String extensionWithDot)
  {
    return new File(getSourceFilePath() + "/" + getApprovalName() + Writer.received + extensionWithDot);
  }

  public File getApprovalFile(String extensionWithDot)
  {
    return new File(getSourceFilePath() + "/" + getApprovalName() + Writer.approved + extensionWithDot);
  }

  @Override
  public File getApprovedFile(String extensionWithDot)
  {
    return getApprovalFile(extensionWithDot);
  }

  @Override
  public ApprovalNamer addAdditionalInformation(String info)
  {
    return new NamerWrapper(((ApprovalNamer) approvalBaseName).addAdditionalInformation(info),
        ((ApprovalNamer) sourceFilePath).addAdditionalInformation(info));
  }

  @Override
  public String getAdditionalInformation()
  {
    return ((ApprovalNamer) this.approvalBaseName).getAdditionalInformation();
  }
}
