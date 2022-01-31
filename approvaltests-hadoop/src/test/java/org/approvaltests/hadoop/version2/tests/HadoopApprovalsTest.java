package org.approvaltests.hadoop.version2.tests;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.approvaltests.hadoop.version2.HadoopApprovals;
import org.approvaltests.hadoop.version2.MapperWrapper;
import org.approvaltests.hadoop.version2.ReducerWrapper;
import org.approvaltests.hadoop.version2.SmartMapper;
import org.approvaltests.hadoop.version2.SmartReducer;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;

import com.spun.util.ArrayUtils;

import junit.framework.TestCase;

@UseReporter({DiffReporter.class})
public class HadoopApprovalsTest extends TestCase
{
  public void testMap() throws Exception
  {
    SmartMapper<LongWritable, Text, Text, LongWritable> mapper = new MapperWrapper<>(new WordCountMap(),
        LongWritable.class, Text.class, Text.class, LongWritable.class);
    HadoopApprovals.verifyMapping(mapper, 0, "llew gen  llew");
  }
  public void testReduce() throws Exception
  {
    SmartReducer<Text, LongWritable, Text, LongWritable> reducer = new ReducerWrapper<>(new WordCountReduce(),
        Text.class, LongWritable.class, Text.class, LongWritable.class);
    HadoopApprovals.verifyReducer(reducer, "life", 7, 35);
  }
  public void testMapReducer() throws Exception
  {
    SmartMapper<LongWritable, Text, Text, LongWritable> mapper = new MapperWrapper<>(new WordCountMap(),
        LongWritable.class, Text.class, Text.class, LongWritable.class);
    SmartReducer<Text, LongWritable, Text, LongWritable> reducer = new ReducerWrapper<>(new WordCountReduce(),
        Text.class, LongWritable.class, Text.class, LongWritable.class);
    HadoopApprovals.verifyMapReduce(mapper, reducer, 0, "one fish two fish red fish blue fish");
  }
  public static class WordCountReduce extends MapReduceBase
      implements
        Reducer<Text, LongWritable, Text, LongWritable>
  {
    public void reduce(Text token, Iterator<LongWritable> counts, OutputCollector<Text, LongWritable> context,
        Reporter arg3) throws IOException
    {
      long n = 0;
      for (LongWritable count : ArrayUtils.asIterable(counts))
      {
        n += count.get();
      }
      context.collect(token, new LongWritable(n));
    }
  }
  private static class WordCountMap extends MapReduceBase implements Mapper<LongWritable, Text, Text, LongWritable>
  {
    public void map(LongWritable __, Text text, OutputCollector<Text, LongWritable> context, Reporter reporter)
        throws IOException
    {
      for (String token : text.toString().split("\\s+"))
      {
        context.collect(new Text(token), new LongWritable(1));
      }
    }
  }
}
