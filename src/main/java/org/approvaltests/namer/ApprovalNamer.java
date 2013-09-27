package org.approvaltests.namer;

public interface ApprovalNamer
{
  /**
   * Returns approval name.
   * @return approval name.
   */
  String getApprovalName();
  
  /**
   * Returns base path where approval file is to be created.
   * @return base path where approval file is to be created.
   */
  String getApprovalFileBasePath();
}
