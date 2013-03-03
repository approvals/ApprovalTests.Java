package org.approvaltests.hadoop;

public interface Transform<KeyIn, ValueIn, KeyOut, ValueOut>
{
  public Class<KeyIn> getKeyInType();
  public Class<ValueIn> getValueInType();
  public Class<KeyOut> getKeyOutType();
  public Class<ValueOut> getValueOutType();
}
