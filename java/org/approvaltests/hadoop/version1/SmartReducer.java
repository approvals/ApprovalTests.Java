package org.approvaltests.hadoop.version1;

import org.apache.hadoop.mapreduce.Reducer;
import org.approvaltests.hadoop.Transform;

public abstract class SmartReducer<KeyIn, ValueIn, KeyOut, ValueOut>
    extends
      Reducer<KeyIn, ValueIn, KeyOut, ValueOut> implements Transform<KeyIn, ValueIn, KeyOut, ValueOut>
{
}