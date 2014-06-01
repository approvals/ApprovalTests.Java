package org.approvaltests.hadoop.version2.tests;

import junit.framework.TestCase;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.approvaltests.hadoop.WritableUtils;

public class WritableConversionTest extends TestCase
{
  public void testConversion() throws Exception
  {
    AssertConvert(5, LongWritable.class);
    AssertConvert(5, DoubleWritable.class);
    AssertConvert(5, IntWritable.class);
  }
  private <T> void AssertConvert(Object value, Class<T> clazz) throws Exception
  {
    T out = WritableUtils.createWritable(value, clazz);
    assertEquals(clazz.getName(), out.getClass().getName());
  }
}
