package org.approvaltests.hadoop.version1;

import org.apache.hadoop.mapreduce.Mapper;
import org.approvaltests.hadoop.Transform;

public abstract class SmartMapper<KeyIn, ValueIn, KeyOut, ValueOut>
    extends
      Mapper<KeyIn, ValueIn, KeyOut, ValueOut> implements Transform<KeyIn, ValueIn, KeyOut, ValueOut>
{
}
