package org.approvaltests.namer;

public class NamerWrapper implements ApprovalNamer
{
  private final GetApprovalName approvalBaseName;
  private final GetSourceFilePath sourceFilePath;
  public NamerWrapper(GetApprovalName approvalBaseName, GetSourceFilePath sourceFilePath)
  {
    this.approvalBaseName = approvalBaseName;
    this.sourceFilePath = sourceFilePath;
  }
  public NamerWrapper(ApprovalNamer namer)
  {
    this.approvalBaseName = (GetApprovalName) namer;
    this.sourceFilePath = (GetSourceFilePath) namer;
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
}
