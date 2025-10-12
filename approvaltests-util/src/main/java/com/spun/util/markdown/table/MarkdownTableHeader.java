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
    int dashCount = Math.max(3, padUntil + 2);
    String front = "";
    String back = "";
    if (5 <= dashCount)
    {
      front = " ";
      back = " ";
      dashCount -= 2;
    }
    if (columnProperties == MarkdownColumn.RIGHT_JUSTIFIED)
    {
      dashCount--;
      back = ":" + back;
    }
    if (columnProperties == MarkdownColumn.LEFT_JUSTIFIED)
    {
      dashCount--;
      front = front + ":";
    }
    String dashes = StringUtils.repeat("-", dashCount);
    return front + dashes + back;
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
