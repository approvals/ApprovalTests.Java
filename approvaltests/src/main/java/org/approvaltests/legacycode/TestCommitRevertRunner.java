package org.approvaltests.legacycode;

import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class TestCommitRevertRunner extends BlockJUnit4ClassRunner
{
  public TestCommitRevertRunner(Class<?> klass) throws InitializationError
  {
    super(klass);
  }
  @Override
  public void run(RunNotifier notifier)
  {
    RunListener l = new RunListener()
    {
      public void testRunFinished(Result result) throws Exception
      {
        super.testRunFinished(result);
        TestCommitRevert.doCommitOrRevert(result.getFailureCount());
      }
    };
    notifier.addListener(l);
    super.run(notifier);
  }
}
