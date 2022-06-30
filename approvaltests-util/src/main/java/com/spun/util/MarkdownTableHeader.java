package com.spun.util;

public class MarkdownTableHeader implements MarkdownTableElement, Resizable
{
  private int padUntil = 1;
  public MarkdownTableHeader()
  {
  }
  @Override
  public String toString()
  {
    int dashes = padUntil + 2; // Math.max(3, padUntil + 2);
    return "----------------------------------".substring(0, dashes);
  }
  public int getLength()
  {
    return 1;
  }
  public void setPadding(int length)
  {
    padUntil = length;
  }
}
