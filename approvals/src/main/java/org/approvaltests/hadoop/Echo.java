package org.approvaltests.hadoop;

import org.lambda.functions.Function1;

public class Echo implements Function1<Object, String>
{
  public static final Echo INSTANCE = new Echo();
  @Override
  public String call(Object in)
  {
    return "" + in;
  }
}