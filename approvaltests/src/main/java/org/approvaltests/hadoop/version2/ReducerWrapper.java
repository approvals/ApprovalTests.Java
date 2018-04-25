package org.approvaltests.hadoop.version2;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class ReducerWrapper<KeyIn, ValueIn, KeyOut, ValueOut>
    implements
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
  public void reduce(KeyIn arg0, Iterator<ValueIn> arg1, OutputCollector<KeyOut, ValueOut> arg2, Reporter arg3)
      throws IOException
  {
    reducer.reduce(arg0, arg1, arg2, arg3);
  }
  @Override
  public void configure(JobConf arg0)
  {
    reducer.configure(arg0);
  }
  @Override
  public void close() throws IOException
  {
    reducer.close();
  }
}
