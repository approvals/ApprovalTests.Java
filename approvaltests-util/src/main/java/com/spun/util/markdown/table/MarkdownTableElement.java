package com.spun.util.markdown.table;

public interface MarkdownTableElement
{
  MarkdownTableElement DELIMITER = new MarkdownTableElement()
                                 {
                                   @Override
                                   public String toString()
                                   {
                                     return "|";
                                   }
                                 };
  MarkdownTableElement NEWLINE   = new MarkdownTableElement()
                                 {
                                   @Override
                                   public String toString()
                                   {
                                     return "\n";
                                   }
                                 };
}
