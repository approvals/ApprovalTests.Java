package org.approvaltests.legacycode;

import java.util.List;

import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

public class TestCommitRevertRunner extends BlockJUnit4ClassRunner
{
  public TestCommitRevertRunner(Class<?> klass) throws InitializationError
  {
    super(klass);
  }
  @Override
  protected List<MethodRule> rules(Object target)
  {
    List<MethodRule> e = super.rules(target);
    e.add(new TestWatchman()
    {
      public void starting(FrameworkMethod method)
      {
        // nothing
      }
      public void succeeded(FrameworkMethod method)
      {
      }
      public void failed(Throwable e, FrameworkMethod method)
      {
        TestCommitRevert.failures++;
      }
    });
    return e;
  }
  @Override
  protected Statement methodInvoker(FrameworkMethod method, Object test)
  {
    System.out.println("invoking: " + method.getName());
    Statement result = super.methodInvoker(method, test);
    return result;
  }
  @Override
  protected Statement withAfterClasses(Statement statement)
  {
    TestCommitRevert.after();
    Statement fromClass = super.withAfterClasses(statement);
    return fromClass;
  }
}
