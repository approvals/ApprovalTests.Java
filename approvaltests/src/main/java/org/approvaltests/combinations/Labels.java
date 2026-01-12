package org.approvaltests.combinations;

import com.spun.util.ArrayUtils;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public class Labels
{
  private enum Fields {
                       HEADER, LABELS;
  }
  private final Map<Fields, Object> fields = new EnumMap<>(Fields.class);
  public Labels()
  {
  }

  private Labels(Map<Fields, Object> fields, Fields key, Object value)
  {
    this.fields.putAll(fields);
    this.fields.put(key, value);
  }

  public Labels withHeader(String header)
  {
    return new Labels(fields, Fields.HEADER, header);
  }

  public Labels withLabels(String... labelNames)
  {
    return new Labels(fields, Fields.LABELS, copyLabels(labelNames));
  }

  public String getHeader()
  {
    return ArrayUtils.getOrElse(fields, Fields.HEADER, () -> "");
  }

  public boolean hasHeader()
  {
    return !getHeader().isEmpty();
  }

  public String[] getLabels()
  {
    return ArrayUtils.getOrElse(fields, Fields.LABELS, () -> new String[0]);
  }

  public boolean hasLabels()
  {
    return getLabels().length > 0;
  }

  private static String[] copyLabels(String[] labelNames)
  {
    if (labelNames == null || labelNames.length == 0)
    { return new String[0]; }
    return Arrays.copyOf(labelNames, labelNames.length);
  }
}
