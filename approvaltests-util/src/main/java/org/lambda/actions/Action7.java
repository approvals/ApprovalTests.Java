package org.lambda.actions;

public interface Action7<In1, In2, In3, In4, In5, In6, In7>
{
  public static <In1, In2, In3, In4, In5, In6, In7> Action7<In1, In2, In3, In4, In5, In6, In7> doNothing()
  {
    return (In1, In2, In3, In4, In5, In6, In7) -> {
    };
  }
  public void call(In1 a, In2 b, In3 c, In4 d, In5 e, In6 f, In7 g);
}
