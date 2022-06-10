package org.approvaltests;

import org.approvaltests.legacycode.Range;
import org.approvaltests.utils.MarkdownTable;
import org.junit.jupiter.api.Test;

public class TableTest
{
  @Test
  void abilityModifier()
  {
    // begin-snippet: markdown_table_example
    Integer[] inputs = Range.get(1, 20);
    MarkdownTable table = MarkdownTable.create(inputs, a -> getModifier(a), "Score", "Modifier");
    Approvals.verify(table);
    // end-snippet
  }
  private Integer getModifier(Integer ability)
  {
    return ability / 2 - 5;
  }
}
