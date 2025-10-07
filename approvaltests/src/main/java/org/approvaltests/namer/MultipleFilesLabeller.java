package org.approvaltests.namer;

import org.lambda.functions.Function0;

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
