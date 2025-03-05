package com.spun.util.markdown.table;

import com.spun.util.StringUtils;

public class MarkdownTableHeader implements MarkdownTableElement, Resizable
{
  private int            padUntil = 1;
  private MarkdownColumn columnProperties;
  public MarkdownTableHeader()
  {
  }
  @Override
  public String toString()
  {
    int dashCount = padUntil + 2; // Math.max(3, padUntil + 2);
    String dashes = StringUtils.repeat("-", dashCount);
    if (columnProperties == MarkdownColumn.RIGHT_JUSTIFIED)
    {
      dashes = dashes.substring(0, dashCount - 1) + ":";
    }
    if (columnProperties == MarkdownColumn.LEFT_JUSTIFIED)
    {
      dashes = ":" + dashes.substring(0, dashCount - 1);
    }
    return dashes;
  }
  public int getLength()
  {
    return 1;
  }
  public void setPadding(int length)
  {
    padUntil = length;
  }
  @Override
  public void setJustification(MarkdownColumn columnProperties)
  {
    this.columnProperties = columnProperties;
  }
}
