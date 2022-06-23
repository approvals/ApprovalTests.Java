package com.spun.util;

import org.lambda.functions.Function1;
import org.lambda.query.Queryable;

public class MarkdownTable implements MarkdownCompatible
{
  protected String markdown;
  public static <I, O> MarkdownTable create(I[] inputs, Function1<I, O> o, String column1, String column2)
  {
    MarkdownTable table = new MarkdownTable().withColumnHeaders(column1, column2);
    for (I input : inputs)
    {
      table.addRow(input, o.call(input));
    }
    return table;
  }
  public static MarkdownTable withHeaders(String... columnNames)
  {
    MarkdownTable table = new MarkdownTable();
    return table.withColumnHeaders(columnNames);
  }
  public <I> MarkdownTable addRowsForInputs(I[] inputs, Function1<I, Object>... transfers)
  {
    for (I input : inputs)
    {
      Queryable<Object> row = Queryable.as(transfers).select(f -> f.call(input));
      row.add(0, input);
      addRow(row.toArray());
    }
    return this;
  }
  public MarkdownTable addRow(Object... columns)
  {
    markdown += printRow(columns);
    return this;
  }
  public MarkdownTable withColumnHeaders(String... headers)
  {
    markdown = printColumnHeaders(headers);
    return this;
  }
  @Override
  public String toString()
  {
    return toMarkdown();
  }
  @Override
  public String toMarkdown()
  {
    return markdown;
  }
  public static String printColumnHeaders(String... headers)
  {
    return printRow(headers) + printRow(ArrayUtils.of("---", headers.length));
  }
  public static String printRow(Object... columns)
  {
    StringBuffer b = new StringBuffer();
    b.append("|");
    for (int x = 0; x < columns.length; ++x)
    {
      b.append(String.format(" %s |", columns[x]));
    }
    b.append("\n");
    return b.toString();
  }
}
