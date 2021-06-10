package org.lambda.actions;

public interface Action3WithException<In1, In2, In3>
{
  public void call(In1 a, In2 b, In3 c) throws Throwable;
}
