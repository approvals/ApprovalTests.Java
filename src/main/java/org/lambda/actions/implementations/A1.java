package org.lambda.actions.implementations;

import org.lambda.functions.implementations.F2;

public class A1<In> extends F2<Boolean, In, String> implements org.lambda.actions.Action1<In>
{
  public boolean run;
  public A1(boolean a, In b, Object... extraVariables)
  {
    super(a, b, extraVariables);
    run = a;
  }
  @Override
  public void returnValue(String returnValue)
  {
    throw new Error("Return Values are not allowed for actions");
  }
  @Override
  public void call(In a)
  {
    super.call(true, a);
  }
}
