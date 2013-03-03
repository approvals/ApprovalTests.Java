package org.lambda.actions.implementations;

import org.lambda.functions.implementations.F1;

public class A0 extends F1<Boolean, String> implements org.lambda.actions.Action0
{
  public boolean run;
  public A0(boolean a, Object... extraVariables)
  {
    super(a, extraVariables);
    run = a;
  }
  @Override
  public void returnValue(String returnValue)
  {
    throw new Error("Return Values are not allowed for actions");
  }
  @Override
  public void call()
  {
    super.call(true);
  }
}
