package org.approvaltests.core;

public interface ApprovalReporterWithCleanUp
{
  public void cleanUp(String received, String approved) throws Exception;
}
