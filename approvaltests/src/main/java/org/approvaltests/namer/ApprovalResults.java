package org.approvaltests.namer;

public class ApprovalResults
{

  /**
   * @deprecated Use {@link NamerFactory#asOsSpecificTest()}
   */
  @Deprecated
  public static NamedEnvironment UniqueForOs()
  {
    return NamerFactory.asOsSpecificTest();
  }

  public MultipleFilesLabeller useMultipleFiles()
  {
    MultipleFilesLabeller l = new MultipleFilesLabeller();
    l.next();
    return l;
  }
}
