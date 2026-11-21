package org.approvaltests.namer;

import org.lambda.functions.Function0;

/**
 * @deprecated Use {@code Approvals.NAMES.useMultipleFiles()} and pass {@code counter.next()} to each verify.
 *             See <a href="https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/docs/how_to/UpdateFromNamerFactoryToOptions.md">How to update from NamerFactory to Options</a>
 */
@Deprecated
public class MultipleFilesLabeller implements Function0<String>, AutoCloseable
{
  private int              count = 1;
  private NamedEnvironment last;
  public MultipleFilesLabeller()
  {
    last = NamerFactory.asMachineSpecificTest(this);
  }

  @Override
  public String call()
  {
    return "" + (count++);
  }

  public void next()
  {
    last.close();
    last = NamerFactory.asMachineSpecificTest(this);
  }

  @Override
  public void close()
  {
    last.close();
  }
}
