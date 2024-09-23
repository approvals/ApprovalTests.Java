package org.approvaltests;

public class ApprovalsDuplicateVerifyException extends RuntimeException
{
  public ApprovalsDuplicateVerifyException(String file)
  {
    super("""
        Already approved: %s
        By default, ApprovalTests only allows one verify() call per test.
        To find out more, visit:
        https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/reference/Naming.md

        # Fixes
        1. separate your test into two tests
        2. add NamedParameters with the NamerFactory
        3. Override Approvals.settings() with either
            a. allowMultipleVerifyCallsForThisClass
            b. allowMultipleVerifyCallsForThisMethod""".formatted(file));;
  }
}
