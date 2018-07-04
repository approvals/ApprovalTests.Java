package org.lambda.functions.implementations;

/**
 * @deprecated use lambdas:  (a,b,c,d,e,f,g,h,i) {@literal -> ...your code...}
 */
@Deprecated
public class Function<In1, In2, In3, In4, In5, In6, In7, In8, In9, Out>
{
  public In1 a;
  public In2 b;
  public In3 c;
  public In4 d;
  public In5 e;
  public In6 f;
  public In7 g;
  public In8 h;
  public In9 i;
  public Function(Object[] extraVariables)
  {
  }
  protected Out call(Object[] mainParams)
  {
    return null;
  }
  public void returnValue(Out returnValue)
  {
  }
  public void ret(Out returnValue)
  {
  }
}
