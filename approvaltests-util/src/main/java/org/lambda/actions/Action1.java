package org.lambda.actions;

/**
 * Findable name for java.util.function.Consumer
 **/
public interface Action1<In1>
{
  public static <In1> Action1<In1> doNothing()
  {
    return (In1) -> {
    };
  }
  public void call(In1 a);
}
