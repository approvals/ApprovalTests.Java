package com.spun.util;

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
