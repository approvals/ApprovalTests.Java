package org.lambda.functions.implementations;

/**
 * @deprecated use lambdas:  a -> ...your code...
 */
@Deprecated
public class S1<In> extends F1<In, Comparable>
{
  public S1(In a, Object... extraVariables)
  {
    super(a, extraVariables);
  }
}
