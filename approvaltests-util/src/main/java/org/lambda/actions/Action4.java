package org.lambda.actions;

public interface Action4<In1, In2, In3, In4>
{
  public static <In1, In2, In3, In4> Action4<In1, In2, In3, In4> doNothing()
  {
    return (In1, In2, In3, In4) -> {
    };
  }

  public void call(In1 a, In2 b, In3 c, In4 d);
}
