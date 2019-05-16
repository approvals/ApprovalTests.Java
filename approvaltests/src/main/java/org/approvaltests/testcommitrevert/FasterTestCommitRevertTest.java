package org.approvaltests.testcommitrevert;

public class FasterTestCommitRevertTest extends TestCommitRevertTest
{
  public FasterTestCommitRevertTest()
  {
    GitCommitOrRevert.askForCommitMessage = FasterCommitDialog::getCommitMessageViaAppleScript;
  }
}
