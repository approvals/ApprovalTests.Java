package org.approvaltests.namer;

public class ApprovalResults
{
  public static void UniqueForOs()
  {
    NamerFactory.asMachineSpecificTest(new OsEnvironmentLabeller());
  }
  public MultipleFilesLabeller useMultipleFiles()
  {
    MultipleFilesLabeller l = new MultipleFilesLabeller();
    l.next();
    return l;
  }
}
