package org.approvaltests;

public class ApprovalsDuplicateVerifyException extends RuntimeException
{
  // @formatter:off
  public ApprovalsDuplicateVerifyException(String file)
  {
    super(
        "By default, ApprovalTests only allows one verify() call per test.\n"
        + "This file has already been approved: " + file + "\n"
        + "\n"
        + "You can do one of the following:\n"
        + "\n"
        + "* Separate your test into two tests\n"
        + "* Allow multiple verify calls inside one test class or method via:\n"
        + "\t- Approvals.settings().allowMultipleVerifyCallsForThisClass();\n"
        + "\t- Approvals.settings().allowMultipleVerifyCallsForThisMethod();\n"
        + "* Add NamedParameters with the NamerFactory\n"
        + "\t- visit https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/reference/Naming.md\n"
    );
  }
  // @formatter:on
}
