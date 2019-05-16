package org.lambda.actions;

/**
 * Findable name for java.util.function.BiConsumer
 **/
public interface Action2<In1, In2>
{
  public static <In1, In2> Action2<In1, In2> doNothing()
  {
    return (In1, In2) -> {
    };
  }
  public void call(In1 a, In2 b);
}
