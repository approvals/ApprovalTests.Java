package org.approvaltests.hadoop.version2;

import org.apache.hadoop.mapred.Mapper;
import org.approvaltests.hadoop.Transform;

public interface SmartMapper<KeyIn, ValueIn, KeyOut, ValueOut>
    extends
      Mapper<KeyIn, ValueIn, KeyOut, ValueOut>,
      Transform<KeyIn, ValueIn, KeyOut, ValueOut>
{
}
