package org.approvaltests.hadoop.version2;

import org.apache.hadoop.mapred.Reducer;
import org.approvaltests.hadoop.Transform;

public interface SmartReducer<KeyIn, ValueIn, KeyOut, ValueOut>
    extends
      Reducer<KeyIn, ValueIn, KeyOut, ValueOut>,
      Transform<KeyIn, ValueIn, KeyOut, ValueOut>
{
}