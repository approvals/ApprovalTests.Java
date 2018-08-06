package org.lambda.actions;

/**
 * Findable name for java.util.function.BiConsumer
 **/
public interface Action2<In1, In2>
{
  public void call(In1 a, In2 b);
}
