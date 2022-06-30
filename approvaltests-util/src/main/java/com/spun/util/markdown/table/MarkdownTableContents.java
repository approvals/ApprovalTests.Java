package com.spun.util.markdown.table;

import com.spun.util.StringUtils;

public class MarkdownTableContents implements MarkdownTableElement, Resizable
{
  private String         contents;
  private int            padUntil;
  private MarkdownColumn columnProperties = MarkdownColumn.DEFAULT;
  public MarkdownTableContents(String contents)
  {
    this.contents = contents;
  }
  @Override
  public String toString()
  {
    String c;
    if (columnProperties == MarkdownColumn.RIGHT_JUSTIFIED)
    {
      c = StringUtils.padLeft(contents, padUntil);
    }
    else
    {
      c = StringUtils.pad(contents, padUntil);
    }
    return " " + c + " ";
  }
  public int getLength()
  {
    return contents.length();
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
