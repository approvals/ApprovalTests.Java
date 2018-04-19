package org.approvaltests.hadoop.version1;

import java.io.IOException;

import org.apache.hadoop.mapreduce.Reducer;

public class ReducerWrapper<KeyIn, ValueIn, KeyOut, ValueOut>
    extends
      SmartReducer<KeyIn, ValueIn, KeyOut, ValueOut>
{
  private final Class<KeyIn>                              keyIn;
  private final Class<ValueIn>                            valueIn;
  private final Class<KeyOut>                             keyOut;
  private final Class<ValueOut>                           valueOut;
  private final Reducer<KeyIn, ValueIn, KeyOut, ValueOut> reducer;
  public ReducerWrapper(Reducer<KeyIn, ValueIn, KeyOut, ValueOut> reducer, Class<KeyIn> keyIn,
      Class<ValueIn> valueIn, Class<KeyOut> keyOut, Class<ValueOut> valueOut)
  {
    this.reducer = reducer;
    this.keyIn = keyIn;
    this.valueIn = valueIn;
    this.keyOut = keyOut;
    this.valueOut = valueOut;
  }
  @Override
  public Class<KeyIn> getKeyInType()
  {
    return keyIn;
  }
  @Override
  public Class<ValueIn> getValueInType()
  {
    return valueIn;
  }
  @Override
  public Class<KeyOut> getKeyOutType()
  {
    return keyOut;
  }
  @Override
  public Class<ValueOut> getValueOutType()
  {
    return valueOut;
  }
  @Override
  public void run(Context context) throws IOException, InterruptedException
  {
    reducer.run(context);
  }
}
