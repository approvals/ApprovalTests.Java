package org.approvaltests.hadoop.version2;

import java.io.IOException;

import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class MapperWrapper<KeyIn, ValueIn, KeyOut, ValueOut>
    implements
      SmartMapper<KeyIn, ValueIn, KeyOut, ValueOut>
{
  private final Class<KeyIn>                             keyIn;
  private final Class<ValueIn>                           valueIn;
  private final Class<KeyOut>                            keyOut;
  private final Class<ValueOut>                          valueOut;
  private final Mapper<KeyIn, ValueIn, KeyOut, ValueOut> mapper;
  public MapperWrapper(Mapper<KeyIn, ValueIn, KeyOut, ValueOut> mapper, Class<KeyIn> keyIn,
      Class<ValueIn> valueIn, Class<KeyOut> keyOut, Class<ValueOut> valueOut)
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
  @Override
  public void map(KeyIn arg0, ValueIn arg1, OutputCollector<KeyOut, ValueOut> arg2, Reporter arg3)
      throws IOException
  {
    mapper.map(arg0, arg1, arg2, arg3);
  }
  @Override
  public void configure(JobConf arg0)
  {
    mapper.configure(arg0);
  }
  @Override
  public void close() throws IOException
  {
    mapper.close();
  }
}
