package org.lambda.actions.implementations;

import org.lambda.functions.implementations.F1;

import com.spun.util.DeprecatedException;

/**
 * @deprecated use lambdas:  () {@literal -> ...your code...}
 */
@Deprecated
public class A0 extends F1<Boolean, String> implements org.lambda.actions.Action0
{
  public boolean run;
  public A0(boolean a, Object... extraVariables)
  {
    super(a, extraVariables);
    throw new DeprecatedException("() -> {/*your code*/}");
  }
  @Override
  public void returnValue(String returnValue)
  {
    throw new Error("Return Values are not allowed for actions");
  }
  @Override
  public void call()
  {
    throw new DeprecatedException("() -> {/*your code*/}");
  }
}
