package org.lambda.actions;

public interface Action9<In1, In2, In3, In4, In5, In6, In7, In8, In9>
{
  public static <In1, In2, In3, In4, In5, In6, In7, In8, In9> Action9<In1, In2, In3, In4, In5, In6, In7, In8, In9> doNothing()
  {
    return (In1, In2, In3, In4, In5, In6, In7, In8, In9) -> {
    };
  }

  public void call(In1 a, In2 b, In3 c, In4 d, In5 e, In6 f, In7 g, In8 h, In9 i);
}
