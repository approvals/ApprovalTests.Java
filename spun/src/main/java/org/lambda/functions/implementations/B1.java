package org.lambda.functions.implementations;

/**
 * @deprecated use lambdas:  a -> ...your code...
 */
@Deprecated
public class B1<In> extends F1<In, Boolean>
{
  public B1(In a, Object... extraVariables)
  {
    super(a, extraVariables);
  }
}
