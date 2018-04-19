package org.approvaltests.hadoop.version1.tests;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.approvaltests.hadoop.version1.HadoopApprovals;
import org.approvaltests.hadoop.version1.MapperWrapper;
import org.approvaltests.hadoop.version1.ReducerWrapper;
import org.approvaltests.hadoop.version1.SmartMapper;
import org.approvaltests.hadoop.version1.SmartReducer;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;

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
  private static class WordCountReduce extends Reducer<Text, LongWritable, Text, LongWritable>
  {
    @Override
    protected void reduce(Text token, Iterable<LongWritable> counts, Context context)
        throws IOException, InterruptedException
    {
      long n = 0;
      for (LongWritable count : counts)
        n += count.get();
      context.write(token, new LongWritable(n));
    }
  }
  private static class WordCountMap extends Mapper<LongWritable, Text, Text, LongWritable>
  {
    protected void map(LongWritable offset, Text text, Context context) throws IOException, InterruptedException
    {
      for (String token : text.toString().split("\\s+"))
      {
        context.write(new Text(token), new LongWritable(1));
      }
    }
  }
}
