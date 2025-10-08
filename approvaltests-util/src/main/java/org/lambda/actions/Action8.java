package org.lambda.actions;

public interface Action8<In1, In2, In3, In4, In5, In6, In7, In8>
{
  public static <In1, In2, In3, In4, In5, In6, In7, In8> Action8<In1, In2, In3, In4, In5, In6, In7, In8> doNothing()
  {
    return (In1, In2, In3, In4, In5, In6, In7, In8) -> {
    };
  }

  public void call(In1 a, In2 b, In3 c, In4 d, In5 e, In6 f, In7 g, In8 h);
}
