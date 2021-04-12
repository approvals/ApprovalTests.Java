package com.spun.util;

/**
  * This is a Top Level of the configuration classes. It is a container for
  * other classes.
  **/
public interface EnabledConditions
{
  /**
    * Returns the conditions that are allowed at the current time.
    **/
  public boolean isEnabled(int state);
  /**
    * Returns ToolTipText for the item dependant on the state.
    **/
  public String getToolTipText(int state);
  /**
    * Adds the conditionListener, calls conditions changed on the current listener.
    **/
  public void addConditionListener(ConditionListener listener);
  /**
    * removes the conditionListener
    **/
  public void removeConditionListener(ConditionListener listener);
}