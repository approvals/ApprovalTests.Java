package org.approvaltests.namer;

import com.spun.util.tests.StackTraceReflectionResult;

public class TemplateFields
{
  public static final String APPROVED_OR_RECEIVED           = "approved_or_received";
  public static final String APPROVALS_SUBDIRECTORY         = "approvals_subdirectory";
  public static final String FILE_EXTENSION                 = "file_extension";
  public static final String RELATIVE_TEST_SOURCE_DIRECTORY = "relative_test_source_directory";
  public static final String TEST_CASE_NAME                 = "test_case_name";
  public static final String TEST_FILE_NAME                 = "test_file_name";
  public static final String TEST_SOURCE_DIRECTORY          = "test_source_directory";
  public static interface TemplateFieldsRetriever
  {
    public String getApprovedOrReceived();

    public String getApprovalsSubdirectory();

    public String getFileExtension();

    public String getRelativeTestSourceDirectory();

    public String getTestCaseName();

    public String getTestFileName();

    public String getTestSourceDirectory();
  }
}
