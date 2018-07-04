package org.approvaltests.hadoop.version1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.approvaltests.Approvals;
import org.approvaltests.hadoop.Echo;
import org.approvaltests.hadoop.PairComparer;
import org.approvaltests.hadoop.WritableUtils;

import com.spun.util.ArrayUtils;

/**
 * To use this package you will need to either extended the SmartMapper and SmartWrapper classes,
 * or use the MapperWrapper and ReducerWrapper classes.
 * 
 * @see SmartMapper
 * @see SmartReducer
 * @see MapperWrapper
 * @see ReducerWrapper
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class HadoopApprovals
{
  public static void verifyMapping(SmartMapper mapper, Object key, Object input) throws Exception
  {
    MapDriver mapDriver = new MapDriver();
    mapDriver.setMapper(mapper);
    Object writableKey = WritableUtils.createWritable(key, mapper.getKeyInType());
    Object writableValue = WritableUtils.createWritable(input, mapper.getValueInType());
    mapDriver.withInput(writableKey, writableValue);
    List results = mapDriver.run();
    Collections.sort(results, PairComparer.INSTANCE);
    String header = String.format("[%s]\n\n -> maps via %s to -> \n", input, mapper.getClass().getSimpleName());
    Approvals.verifyAll(header, results, Echo.INSTANCE);
  }
  public static void verifyReducer(SmartReducer reducer, Object key, Object... values) throws Exception
  {
    List list = new ArrayList();
    for (Object value : values)
    {
      list.add(WritableUtils.createWritable(value, reducer.getValueInType()));
    }
    ReduceDriver reduceDriver = new ReduceDriver<Text, LongWritable, Text, LongWritable>();
    reduceDriver.withInput(WritableUtils.createWritable(key, reducer.getKeyInType()), list);
    reduceDriver.setReducer(reducer);
    List results = reduceDriver.run();
    Collections.sort(results, PairComparer.INSTANCE);
    String header = String.format("(%s, %s)\n\n -> reduces via %s to -> \n", key, list,
        reducer.getClass().getSimpleName());
    Approvals.verifyAll(header, results, Echo.INSTANCE);
  }
  public static void verifyMapReduce(SmartMapper mapper, SmartReducer reducer, Object key, Object input)
      throws Exception
  {
    MapDriver mapDriver = new MapDriver();
    mapDriver.setMapper(mapper);
    MapReduceDriver mapReduceDriver = new MapReduceDriver();
    mapReduceDriver.setMapper(mapper);
    Object writableKey = WritableUtils.createWritable(key, mapper.getKeyInType());
    Object writableValue = WritableUtils.createWritable(input, mapper.getValueInType());
    mapDriver.withInput(writableKey, writableValue);
    List results = mapDriver.run();
    Collections.sort(results, PairComparer.INSTANCE);
    mapReduceDriver = new MapReduceDriver<LongWritable, Text, Text, LongWritable, Text, LongWritable>();
    writableKey = WritableUtils.createWritable(key, mapper.getKeyInType());
    writableValue = WritableUtils.createWritable(input, mapper.getValueInType());
    mapReduceDriver.withInput(writableKey, writableValue);
    mapReduceDriver.setMapper(mapper);
    mapReduceDriver.setReducer(reducer);
    List finalResults = mapReduceDriver.run();
    String text = String.format("[%s]\n\n -> maps via %s to -> \n\n%s\n\n -> reduces via %s to -> \n\n%s", input,
        mapper.getClass().getSimpleName(), ArrayUtils.toString(results, Echo.INSTANCE),
        reducer.getClass().getSimpleName(), ArrayUtils.toString(finalResults, Echo.INSTANCE));
    Approvals.verify(text);
  }
}
