package org.lambda.actions;

public interface Action6<In1, In2, In3, In4, In5, In6>
{
  public static <In1, In2, In3, In4, In5, In6> Action6<In1, In2, In3, In4, In5, In6> doNothing()
  {
    return (In1, In2, In3, In4, In5, In6) -> {
    };
  }

  public void call(In1 a, In2 b, In3 c, In4 d, In5 e, In6 f);
}
