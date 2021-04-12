package org.approvaltests.namer;

public class ApprovalResults
{
  /**
   * @deprecated Use {@link NamerFactory#useMultipleFiles()} instead.
   */
  @Deprecated
  public MultipleFilesLabeller useMultipleFiles()
  {
    MultipleFilesLabeller l = new MultipleFilesLabeller();
    l.next();
    return l;
  }
}
