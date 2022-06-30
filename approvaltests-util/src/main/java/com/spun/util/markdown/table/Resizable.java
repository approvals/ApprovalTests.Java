package com.spun.util.markdown.table;

public interface Resizable
{
  public int getLength();
  public void setPadding(int length);
  public void setJustification(MarkdownColumn columnProperties);
}
