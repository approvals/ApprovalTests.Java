package org.lambda.actions;

public interface Action5<In1, In2, In3, In4, In5>
{
  public static <In1, In2, In3, In4, In5> Action5<In1, In2, In3, In4, In5> doNothing()
  {
    return (In1, In2, In3, In4, In5) -> {
    };
  }

  public void call(In1 a, In2 b, In3 c, In4 d, In5 e);
}
