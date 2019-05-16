package org.approvaltests.testcommitrevert;

import org.junit.runners.model.InitializationError;

public class FasterTestCommitRevertRunner extends TestCommitRevertRunner
{
  public FasterTestCommitRevertRunner(Class<?> clazz) throws InitializationError
  {
    super(clazz);
    GitCommitOrRevert.askForCommitMessage = FasterCommitDialog::getCommitMessageViaAppleScript;
  }
}
