package org.lambda.actions;

public interface Action3<In1, In2, In3>
{
  public static <In1, In2, In3> Action3<In1, In2, In3> doNothing()
  {
    return (In1, In2, In3) -> {
    };
  }

  // begin-snippet: action3_call
  public void call(In1 a, In2 b, In3 c);
  // end-snippet
}
