package org.approvaltests;

import org.lambda.utils.Range;
import org.approvaltests.utils.VerifiableMarkdownTable;
import org.junit.jupiter.api.Test;

public class TableTest
{
  @Test
  void abilityModifier()
  {
    Integer[] inputs = Range.get(1, 20);
    VerifiableMarkdownTable table = VerifiableMarkdownTable.create(inputs, a -> getModifier(a), "Score",
        "Modifier");
    Approvals.verify(table);
  }

  @Test
  void veryLongHeaders()
  {
    Integer[] inputs = Range.get(1, 2);
    VerifiableMarkdownTable table = VerifiableMarkdownTable.create(inputs, a -> getModifier(a),
        "The name is very long and it is a header", "Modifier");
    Approvals.verify(table);
  }

  @Test
  void differentCases()
  {
    // begin-snippet: markdown_table_example
    String[] inputs = {"verify json", "verify all", "verify parameters", "verify as json"};
    VerifiableMarkdownTable table = VerifiableMarkdownTable.withHeaders("Input", "Camel Case", "Snake Case",
        "Kebab Case");
    table.addRowsForInputs(inputs, this::toCamelCase, this::toSnakeCase, this::toKebabCase);
    Approvals.verify(table);
    // end-snippet
  }

  private String toKebabCase(String input)
  {
    String[] parts = input.split(" ");
    return String.join("-", parts);
  }

  private String toSnakeCase(String input)
  {
    String[] parts = input.split(" ");
    return String.join("_", parts);
  }

  private String toCamelCase(String input)
  {
    String[] parts = input.split(" ");
    String out = parts[0];
    for (int i = 1; i < parts.length; i++)
    {
      out += Character.toUpperCase(parts[i].charAt(0)) + parts[i].substring(1);
    }
    return out;
  }

  private Integer getModifier(Integer ability)
  {
    return ability / 2 - 5;
  }
}
