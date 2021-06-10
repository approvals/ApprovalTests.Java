package org.lambda.actions;

/**
 * Findable name for java.util.function.Consumer
 **/
public interface Action1WithException<In1>
{
  public void call(In1 a) throws Throwable;
}
