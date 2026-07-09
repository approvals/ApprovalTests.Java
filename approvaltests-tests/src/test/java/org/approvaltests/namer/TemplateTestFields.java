package org.approvaltests.namer;

public class TemplateTestFields implements TemplateFields.TemplateFieldsRetriever
{
  @Override
  public String getApprovedOrReceived()
  {
    return "approved";
  }

  @Override
  public String getApprovalsSubdirectory()
  {
    return "approvals/subdirectory";
  }

  @Override
  public String getFileExtension()
  {
    return "txt";
  }

  @Override
  public String getRelativeTestSourceDirectory()
  {
    return "src/test/java";
  }

  @Override
  public String getTestCaseName()
  {
    return "testCaseName";
  }

  @Override
  public String getTestFileName()
  {
    return "TestFileName";
  }

  @Override
  public String getTestSourceDirectory()
  {
    return "/my/source/directory";
  }
}
