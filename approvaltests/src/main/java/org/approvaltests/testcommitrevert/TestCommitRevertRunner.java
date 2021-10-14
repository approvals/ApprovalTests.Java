package org.approvaltests.testcommitrevert;

import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * @deprecated Use https://github.com/LarsEckart/tcr-extension instead
 */
@Deprecated()
public class TestCommitRevertRunner extends BlockJUnit4ClassRunner
{
  public TestCommitRevertRunner(Class<?> klass) throws InitializationError
  {
    super(klass);
  }
}
