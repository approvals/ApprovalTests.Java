package com.spun.util.markdown.table;

import org.approvaltests.Approvals;
import org.approvaltests.utils.VerifiableMarkdownTable;
import org.junit.jupiter.api.Test;

public class MarkdownTableTest
{
  @Test
  void testRightJustified()
  {
    VerifiableMarkdownTable table = VerifiableMarkdownTable.withHeaders("Number");
    table.setColumnProperties(MarkdownColumn.RIGHT_JUSTIFIED);
    for (int i = 9; i <= 11; i++)
    {
      table.addRow(i);
    }
    Approvals.verify(table);
  }

  @Test
  void testDefaultJustification()
  {
    VerifiableMarkdownTable table = VerifiableMarkdownTable.withHeaders("Number", "Number++");
    for (int i = 9; i <= 11; i++)
    {
      table.addRow(i, i + 1);
    }
    Approvals.verify(table);
  }
}
