package org.approvaltests.namer;

import org.lambda.functions.Function0;

public class MultipleFilesLabeller implements Function0<String>
{
  private int count = 1;
  @Override
  public String call()
  {
    return "" + (count++);
  }
  public void next()
  {
    NamerFactory.asMachineSpecificTest(this);
  }
}