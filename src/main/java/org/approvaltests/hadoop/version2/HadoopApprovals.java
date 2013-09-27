package org.approvaltests.hadoop.version2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.MapDriver;
import org.apache.hadoop.mrunit.MapReduceDriver;
import org.apache.hadoop.mrunit.ReduceDriver;
import org.approvaltests.Approvals;
import org.approvaltests.hadoop.Echo;
import org.approvaltests.hadoop.PairComparer;
import org.approvaltests.hadoop.WritableUtils;

import com.spun.util.ArrayUtils;

/**
 * To use this package you will need:
 * <ul>
 * <li>hadoop-client-1.0.3.jar</li>
 * <li>hadoop-core-0.20.2.jar</li>
 * <li>hadoop-mapred-test-0.22.0.jar</li>
 * <li>log4j-1.2.15.jar</li>
 * <li>mockito-all-1.8.5.jar</li>
 * <li>mrunit-0.9.0-incubating-hadoop1.jar</li>
 * </ul>
 * in your build path.
 * 
 * You will also want to either extended the SmartMapper & SmartWrapper classes,or use the MapperWrapper & ReducerWrapper classes.
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
    String header = String.format("[%s]\r\n\r\n -> maps via %s to -> \r\n", input, mapper.getClass()
        .getSimpleName());
    Approvals.verifyAll(header, results, Echo.INSTANCE);
  }
  public static <KeyIn, ValueIn, KeyOut, ValueOut> void verifyReducer(
      SmartReducer<KeyIn, ValueIn, KeyOut, ValueOut> reducer, Object key, Object... values) throws Exception
  {
    List<ValueIn> list = new ArrayList<ValueIn>();
    for (Object value : values)
    {
      list.add(WritableUtils.createWritable(value, reducer.getValueInType()));
    }
    ReduceDriver reduceDriver = new ReduceDriver<Text, LongWritable, Text, LongWritable>();
    reduceDriver.withInput(WritableUtils.createWritable(key, reducer.getKeyInType()), list);
    reduceDriver.setReducer(reducer);
    List results = reduceDriver.run();
    Collections.sort(results, PairComparer.INSTANCE);
    String header = String.format("(%s, %s)\r\n\r\n -> reduces via %s to -> \r\n", key, list, reducer.getClass()
        .getSimpleName());
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
    String text = String.format(
        "[%s]\r\n\r\n -> maps via %s to -> \r\n\r\n%s\r\n\r\n -> reduces via %s to -> \r\n\r\n%s", input, mapper
            .getClass().getSimpleName(), ArrayUtils.toString(results, Echo.INSTANCE), reducer.getClass()
            .getSimpleName(), ArrayUtils.toString(finalResults, Echo.INSTANCE));
    Approvals.verify(text);
  }
}
