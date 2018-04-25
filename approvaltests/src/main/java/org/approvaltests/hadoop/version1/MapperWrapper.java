package org.approvaltests.hadoop.version1;

import java.io.IOException;

import org.apache.hadoop.mapreduce.Mapper;

public class MapperWrapper<KeyIn, ValueIn, KeyOut, ValueOut> extends SmartMapper<KeyIn, ValueIn, KeyOut, ValueOut>
{
  private final Class<KeyIn>                             keyIn;
  private final Class<ValueIn>                           valueIn;
  private final Class<KeyOut>                            keyOut;
  private final Class<ValueOut>                          valueOut;
  private final Mapper<KeyIn, ValueIn, KeyOut, ValueOut> mapper;
  public MapperWrapper(Mapper<KeyIn, ValueIn, KeyOut, ValueOut> mapper, Class<KeyIn> keyIn, Class<ValueIn> valueIn,
      Class<KeyOut> keyOut, Class<ValueOut> valueOut)
  {
    this.mapper = mapper;
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
  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void run(org.apache.hadoop.mapreduce.Mapper.Context context) throws IOException, InterruptedException
  {
    mapper.run(context);
  }
}
