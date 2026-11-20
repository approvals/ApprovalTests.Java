package org.approvaltests.namer;

public class TemplateFieldValues
{
  public final String approvedOrReceived;
  public final String approvalsSubdirectory;
  public final String fileExtension;
  public final String relativeTestSourceDirectory;
  public final String testCaseName;
  public final String testFileName;
  public final String testSourceDirectory;
  public final String additionalInformation;
  public TemplateFieldValues(String approvedOrReceived, String approvalsSubdirectory, String fileExtension,
      String relativeTestSourceDirectory, String testCaseName, String testFileName, String testSourceDirectory,
      String additionalInformation)
  {
    this.approvedOrReceived = approvedOrReceived;
    this.approvalsSubdirectory = approvalsSubdirectory;
    this.fileExtension = fileExtension;
    this.relativeTestSourceDirectory = relativeTestSourceDirectory;
    this.testCaseName = testCaseName;
    this.testFileName = testFileName;
    this.testSourceDirectory = testSourceDirectory;
    this.additionalInformation = additionalInformation;
  }

  public TemplateFieldValues with(String approvedOrReceived, String fileExtension)
  {
    return new TemplateFieldValues(approvedOrReceived, approvalsSubdirectory, fileExtension,
        relativeTestSourceDirectory, testCaseName, testFileName, testSourceDirectory, additionalInformation);
  }

  public TemplateFieldValues withAdditionalInformation(String additionalInformation)
  {
    return new TemplateFieldValues(approvedOrReceived, approvalsSubdirectory, fileExtension,
        relativeTestSourceDirectory, testCaseName, testFileName, testSourceDirectory, additionalInformation);
  }

  public static TemplateFieldValues from(TemplateFields.TemplateFieldsRetriever fields)
  {
    return new TemplateFieldValues(fields.getApprovedOrReceived(), fields.getApprovalsSubdirectory(),
        fields.getFileExtension(), fields.getRelativeTestSourceDirectory(), fields.getTestCaseName(),
        fields.getTestFileName(), fields.getTestSourceDirectory(), fields.getAdditionalInformation());
  }
}
