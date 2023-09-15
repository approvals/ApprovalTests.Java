package org.approvaltests;

public class ApprovalsDuplicateVerifyException extends RuntimeException
{
  public ApprovalsDuplicateVerifyException(String file)
  {
    super("Already approved: " + file + "\n"
        + "By default, ApprovalTests only allows one verify() call per test.\n" + "To find out more, visit: \n"
        + "https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/reference/Naming.md\n\n"
        + "# Fixes\n" + "1. separate your test into two tests\n" + "2. add NamedParameters with the NamerFactory\n"
        + "3. Override Approvals.settings() with either \n" + "\ta. allowMultipleVerifyCallsForThisClass\n"
        + "\tb. allowMultipleVerifyCallsForThisMethod");
  }
}
